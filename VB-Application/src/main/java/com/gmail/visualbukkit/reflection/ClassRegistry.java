package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.maven.MavenUtil;
import com.google.common.reflect.ClassPath;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.resolution.DependencyResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.filter.DependencyFilterUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.zeroturnaround.zip.ZipUtil;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ClassRegistry {

    private static final Map<String, ClassInfo> classes = new ConcurrentHashMap<>();
    private static final Set<RemoteRepository> mavenRepositories = ConcurrentHashMap.newKeySet();

    public static void register(JSONObject json) {
        classes.putIfAbsent(json.getString("name"), new JsonClassInfo(json));
    }

    public static void register(ClassLoader classLoader, String zipFile) {
        ZipUtil.iterate(classLoader.getResourceAsStream(zipFile), (inputStream, zipEntry) -> register(new JSONObject(new JSONTokener(inputStream))));
    }

    public static void register(Class<?> clazz) {
        classes.putIfAbsent(clazz.getCanonicalName(), new LoadedClassInfo(clazz));
    }

    public static void register(RemoteRepository repository) {
        mavenRepositories.add(repository);
    }

    public static void register(Dependency dependency) throws IOException, MavenInvocationException, DependencyResolutionException {
        DefaultServiceLocator serviceLocator = MavenRepositorySystemUtils.newServiceLocator();
        serviceLocator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        serviceLocator.addService(TransporterFactory.class, FileTransporterFactory.class);
        serviceLocator.addService(TransporterFactory.class, HttpTransporterFactory.class);

        RepositorySystem repositorySystem = serviceLocator.getService(RepositorySystem.class);
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
        session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session, new LocalRepository(MavenUtil.getMavenHome())));

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(dependency);
        collectRequest.setRepositories(new ArrayList<>(mavenRepositories));
        collectRequest.getRepositories().add(new RemoteRepository.Builder("Maven Central", "default", "https://repo.maven.apache.org/maven2/").build());

        DependencyResult dependencyResult = repositorySystem.resolveDependencies(session, new DependencyRequest(collectRequest, DependencyFilterUtils.classpathFilter(dependency.getScope())));
        List<URL> jarURLs = new ArrayList<>(dependencyResult.getArtifactResults().size());
        for (ArtifactResult artifactResult : dependencyResult.getArtifactResults()) {
            jarURLs.add(artifactResult.getArtifact().getFile().toURI().toURL());
        }
        try (URLClassLoader classLoader = new URLClassLoader(jarURLs.toArray(new URL[0]), null)) {
            register(classLoader);
        }
    }

    public static void register(URLClassLoader classLoader) throws IOException {
        ClassPath classPath = ClassPath.from(classLoader);
        for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
            try {
                if (!classInfo.getPackageName().startsWith("META-INF") && !classInfo.getSimpleName().equals("module-info") && !classInfo.getSimpleName().equals("package-info")) {
                    Class<?> clazz = classInfo.load();
                    if (!clazz.isAnonymousClass() && Modifier.isPublic(clazz.getModifiers())) {
                        register(clazz);
                    }
                }
            } catch (Throwable e) {
                VisualBukkitApp.getLogger().log(Level.WARNING, "Failed to register class", e);
            }
        }
    }

    public static void clear() {
        classes.clear();
        mavenRepositories.clear();
    }

    public static Optional<ClassInfo> getClass(String name) {
        return Optional.ofNullable(classes.get(name));
    }

    public static Collection<ClassInfo> getClasses() {
        return classes.values();
    }

    public static Collection<ClassInfo> getClasses(Predicate<ClassInfo> filter) {
        return classes.values().stream().filter(filter).collect(Collectors.toSet());
    }
}
