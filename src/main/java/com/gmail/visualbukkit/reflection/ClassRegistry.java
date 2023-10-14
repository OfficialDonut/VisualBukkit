package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.PluginBuilder;
import com.google.common.reflect.ClassPath;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.DefaultArtifact;
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
import org.eclipse.aether.util.artifact.JavaScopes;
import org.eclipse.aether.util.filter.DependencyFilterUtils;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ClassRegistry {

    private static final Map<String, ClassInfo> classes = new HashMap<>();
    private static final Set<RemoteRepository> mavenRepositories = new HashSet<>();
    private static final Set<DefaultArtifact> mavenDependencies = new HashSet<>();

    public static void register(DefaultArtifact artifact, RemoteRepository repository, String... packages) throws IOException, MavenInvocationException, DependencyResolutionException {
        mavenDependencies.add(artifact);
        mavenRepositories.add(repository);
        DefaultServiceLocator serviceLocator = MavenRepositorySystemUtils.newServiceLocator();
        serviceLocator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        serviceLocator.addService(TransporterFactory.class, FileTransporterFactory.class);
        serviceLocator.addService(TransporterFactory.class, HttpTransporterFactory.class);

        RepositorySystem repositorySystem = serviceLocator.getService(RepositorySystem.class);
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();
        session.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(session, new LocalRepository(PluginBuilder.getMavenHome())));

        CollectRequest collectRequest = new CollectRequest();
        collectRequest.setRoot(new Dependency(artifact, JavaScopes.COMPILE));
        if (repository != null) {
            collectRequest.setRepositories(Collections.singletonList(repository));
        }

        DependencyResult dependencyResult = repositorySystem.resolveDependencies(session, new DependencyRequest(collectRequest, DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE)));
        List<URL> jarURLs = new ArrayList<>(dependencyResult.getArtifactResults().size());
        for (ArtifactResult artifactResult : dependencyResult.getArtifactResults()) {
            jarURLs.add(artifactResult.getArtifact().getFile().toURI().toURL());
        }
        try (URLClassLoader classLoader = new URLClassLoader(jarURLs.toArray(new URL[0]), null)) {
            register(classLoader, packages);
        }
    }

    public static void register(DefaultArtifact artifact, String... packages) {
        register(artifact, packages);
    }

    public static void register(URLClassLoader classLoader, String... packages) throws IOException {
        ClassPath classPath = ClassPath.from(classLoader);
        for (ClassPath.ClassInfo classInfo : classPath.getAllClasses()) {
            try {
                if (!classInfo.getPackageName().startsWith("META-INF") && !classInfo.getSimpleName().equals("module-info") && !classInfo.getSimpleName().equals("package-info")) {
                    Class<?> clazz = classInfo.load();
                    if (!clazz.isAnonymousClass() && Modifier.isPublic(clazz.getModifiers())) {
                        for (String pkg : packages) {
                            if (clazz.getPackageName().equals(pkg) || classInfo.getPackageName().startsWith(pkg + ".")) {
                                register(clazz);
                                break;
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                VisualBukkitApp.getLogger().log(Level.FINE, "Failed to register class", e);
            }
        }
    }

    public static void register(Class<?> clazz) {
        classes.put(clazz.getCanonicalName(), new ClassInfo(clazz));
    }

    public static void clear() {
        classes.clear();
        mavenDependencies.clear();
        mavenRepositories.clear();
    }

    public static ClassInfo getClass(String name) {
        return classes.get(name);
    }

    public static Set<ClassInfo> getClasses() {
        return new TreeSet<>(classes.values());
    }

    public static Set<ClassInfo> getClasses(Predicate<ClassInfo> filter) {
        return classes.values().stream().filter(filter).collect(Collectors.toCollection(TreeSet::new));
    }

    public static Set<DefaultArtifact> getMavenDependencies() {
        return mavenDependencies;
    }

    public static Set<RemoteRepository> getMavenRepositories() {
        return mavenRepositories;
    }
}
