package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.ui.BackgroundTaskExecutor;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.io.Resources;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.shared.invoker.*;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginBuilder {

    private static final Invoker mavenInvoker = new DefaultInvoker();

    static {
        mavenInvoker.setLogger(new MavenInvokerLogger());
        String installDir = System.getProperty("install4j.appDir");
        if (installDir != null) {
            mavenInvoker.setMavenHome(new File(installDir, "apache-maven"));
        }
    }

    public static void build(Project project) {
        VisualBukkitApp.getLogger().info("Building plugin...");
        VisualBukkitApp.getLogWindow().show();
        BackgroundTaskExecutor.executeAndWait(() -> {
            try {
                String name = project.getPluginName();
                String version = project.getPluginVersion();
                if (name.isBlank()) {
                    name = "Plugin";
                }
                if (version.isBlank()) {
                    version = "1.0";
                }
                String packageName = "vb.$" + name.toLowerCase();

                Path buildDir = project.getBuildDirectory();
                Path mainDir = buildDir.resolve("src").resolve("main");
                Path packageDir = mainDir.resolve("java").resolve("vb").resolve("$" + name.toLowerCase());
                Path resourcesDir = mainDir.resolve("resources");

                if (Files.exists(buildDir)) {
                    MoreFiles.deleteRecursively(buildDir, RecursiveDeleteOption.ALLOW_INSECURE);
                }
                Files.createDirectories(packageDir);
                Files.createDirectories(resourcesDir);

                JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, Resources.toString(PluginBuilder.class.getResource("/plugin/PluginMain.java"), StandardCharsets.UTF_8));
                mainClass.setPackage(packageName);

                if (Files.exists(project.getResourcesDirectory())) {
                    try (Stream<Path> stream = Files.walk(project.getResourcesDirectory())) {
                        for (Path path : stream.toArray(Path[]::new)) {
                            if (Files.isRegularFile(path) && !Files.isHidden(path)) {
                                Path relativePath = project.getResourcesDirectory().relativize(path);
                                Path resourceDirPath = resourcesDir.resolve(relativePath);
                                Files.createDirectories(resourceDirPath.getParent());
                                Files.copy(path, resourceDirPath);
                                String filePath = StringEscapeUtils.escapeJava(relativePath.toString().replace("\\", "/"));
                                MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
                                enableMethod.setBody(enableMethod.getBody() + (filePath.equals("config.yml") ? "saveDefaultConfig();" : ("PluginMain.createResourceFile(\"" + filePath + "\");")));
                            }
                        }
                    }
                }

                BuildInfo buildInfo = new BuildInfo(mainClass);
                for (PluginModule pluginModule : project.getPluginModules()) {
                    pluginModule.prepareBuild(buildInfo);
                }
                for (PluginComponentBlock block : project.getPluginComponents()) {
                    block.prepareBuild(buildInfo);
                }

                Files.writeString(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString());
                Files.writeString(buildDir.resolve("pom.xml"), createPom(name.toLowerCase(), version, buildInfo));
                Files.writeString(resourcesDir.resolve("plugin.yml"), createPluginYml(project, name, version, mainClass.getQualifiedName()));

                InvocationRequest request = new DefaultInvocationRequest();
                request.setOutputHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setErrorHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setBaseDirectory(buildDir.toFile());
                request.setGoals(Arrays.asList("clean", "package"));
                request.setBatchMode(true);
                mavenInvoker.execute(request);
            } catch (Exception e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to build plugin", e);
            }
        });
    }

    public static String getMavenHome() throws MavenInvocationException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DefaultInvocationRequest request = new DefaultInvocationRequest();
        request.addArg("help:evaluate");
        request.addArg("-Dexpression=settings.localRepository");
        request.addArg("-DforceStdout");
        request.setQuiet(true);
        request.setBatchMode(true);
        request.setOutputHandler(new PrintStreamHandler(new PrintStream(baos), false));
        mavenInvoker.execute(request);
        return baos.toString(StandardCharsets.UTF_8).strip();
    }

    private static String createPom(String artifactId, String version, BuildInfo buildInfo) throws IOException {
        return Resources.toString(PluginBuilder.class.getResource("/plugin/pom.xml"), StandardCharsets.UTF_8)
                .replace("{ARTIFACT_ID}", artifactId)
                .replace("{VERSION}", version)
                .replace("{REPOSITORIES}", buildInfo.getMavenRepositories().stream().map(PluginBuilder::getRepositoryString).collect(Collectors.joining("\n        ")))
                .replace("{DEPENDENCIES}", buildInfo.getMavenDependencies().stream().map(PluginBuilder::getDependencyString).collect(Collectors.joining("\n        ")));
    }

    private static String getRepositoryString(RemoteRepository repository) {
        return String.format("<repository><id>%s</id><url>%s</url></repository>", repository.getId(), repository.getUrl());
    }

    private static String getDependencyString(Dependency dependency) {
        return "<dependency>" +
                "<groupId>" + dependency.getArtifact().getGroupId() + "</groupId>" +
                "<artifactId>" + dependency.getArtifact().getArtifactId() + "</artifactId>" +
                "<version>" + dependency.getArtifact().getVersion() + "</version>" +
                "<scope>" + dependency.getScope() + "</scope>" +
                "</dependency>";
    }

    private static String createPluginYml(Project project, String pluginName, String version, String mainClassName) throws IOException {
        String template = Resources.toString(PluginBuilder.class.getResource("/plugin/plugin.yml"), StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder(template.replace("{NAME}", pluginName).replace("{VERSION}", version).replace("{MAIN_CLASS}", mainClassName));
        if (!project.getPluginAuthors().isBlank()) {
            builder.append("authors: [").append(project.getPluginAuthors()).append("]\n");
        }
        if (!project.getPluginDescription().isBlank()) {
            builder.append("description: \"").append(project.getPluginDescription()).append("\"\n");
        }
        if (!project.getPluginWebsite().isBlank()) {
            builder.append("website: \"").append(project.getPluginWebsite()).append("\"\n");
        }
        if (!project.getPluginDependencies().isBlank()) {
            builder.append("depend: [").append(project.getPluginDependencies()).append("]\n");
        }
        if (!project.getPluginSoftDepend().isBlank()) {
            builder.append("softdepend: [").append(project.getPluginSoftDepend()).append("]\n");
        }
        if (!project.getPluginLoadBefore().isBlank()) {
            builder.append("loadbefore: [").append(project.getPluginLoadBefore()).append("]\n");
        }
        if (!project.getPluginPrefix().isBlank()) {
            builder.append("prefix: \"").append(project.getPluginPrefix()).append("\"\n");
        }
        if (!project.getPluginPermissions().isBlank()) {
            builder.append("permissions:\\n  ").append(project.getPluginPermissions().replace("\n", "\n  ")).append("\n");
        }
        return builder.toString();
    }

    private static class MavenInvokerLogger implements InvokerLogger {

        @Override
        public void debug(String s) {
            VisualBukkitApp.getLogger().fine(s);
        }

        @Override
        public void debug(String s, Throwable throwable) {
            VisualBukkitApp.getLogger().log(Level.FINE, s, throwable);
        }

        @Override
        public void info(String s) {
            VisualBukkitApp.getLogger().info(s);
        }

        @Override
        public void info(String s, Throwable throwable) {
            VisualBukkitApp.getLogger().log(Level.INFO, s, throwable);
        }

        @Override
        public void warn(String s) {
            VisualBukkitApp.getLogger().warning(s);
        }

        @Override
        public void warn(String s, Throwable throwable) {
            VisualBukkitApp.getLogger().log(Level.WARNING, s, throwable);
        }

        @Override
        public void error(String s) {
            VisualBukkitApp.getLogger().severe(s);
        }

        @Override
        public void error(String s, Throwable throwable) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, s, throwable);
        }

        @Override
        public void fatalError(String s) {
            VisualBukkitApp.getLogger().severe(s);
        }

        @Override
        public void fatalError(String s, Throwable throwable) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, s, throwable);
        }

        @Override
        public boolean isDebugEnabled() {
            return true;
        }

        @Override
        public boolean isInfoEnabled() {
            return true;
        }

        @Override
        public boolean isWarnEnabled() {
            return true;
        }

        @Override
        public boolean isErrorEnabled() {
            return true;
        }

        @Override
        public boolean isFatalErrorEnabled() {
            return true;
        }

        @Override
        public void setThreshold(int i) {}

        @Override
        public int getThreshold() {
            return 0;
        }
    }
}
