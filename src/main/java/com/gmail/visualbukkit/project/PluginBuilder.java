package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.BackgroundTaskExecutor;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.io.Resources;
import javafx.application.Platform;
import org.apache.maven.shared.invoker.*;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

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
        VisualBukkitApp.getLogWindow().show();
        BackgroundTaskExecutor.executeAndWait(() -> {
            try {
                String name = project.getPluginSettings().getName();
                String version = project.getPluginSettings().getVersion();
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

                BuildInfo buildInfo = new BuildInfo(mainClass);
                buildInfo.getMavenRepositories().addAll(ClassRegistry.getMavenRepositories());
                buildInfo.getMavenDependencies().addAll(ClassRegistry.getMavenDependencies());

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

    private static String getRepositoryString(RemoteRepository repo) {
        return String.format("<repository><id>%s</id><url>%s</url></repository>", repo.getId(), repo.getUrl());
    }

    private static String getDependencyString(DefaultArtifact artifact) {
        StringBuilder builder = new StringBuilder("<dependency>");
        builder.append(String.format("<groupId>%s</groupId><artifactId>%s</artifactId><version>%s</version>", artifact.getGroupId(), artifact.getArtifactId(), artifact.getVersion()));
        for (Map.Entry<String, String> entry : artifact.getProperties().entrySet()) {
            builder.append("<").append(entry.getKey()).append(">").append(entry.getValue()).append("</").append(entry.getKey()).append(">");
        }
        return builder.append("</dependency>").toString();
    }

    private static String createPluginYml(Project project, String pluginName, String version, String mainClassName) throws IOException {
        String template = Resources.toString(PluginBuilder.class.getResource("/plugin/plugin.yml"), StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder(template.replace("{NAME}", pluginName).replace("{VERSION}", version).replace("{MAIN_CLASS}", mainClassName));
        if (!project.getPluginSettings().getAuthors().isBlank()) {
            builder.append("authors: [").append(project.getPluginSettings().getAuthors()).append("]\n");
        }
        if (!project.getPluginSettings().getDescription().isBlank()) {
            builder.append("description: \"").append(project.getPluginSettings().getDescription()).append("\"\n");
        }
        if (!project.getPluginSettings().getWebsite().isBlank()) {
            builder.append("website: \"").append(project.getPluginSettings().getWebsite()).append("\"\n");
        }
        if (!project.getPluginSettings().getDependencies().isBlank()) {
            builder.append("depend: [").append(project.getPluginSettings().getDependencies()).append("]\n");
        }
        if (!project.getPluginSettings().getSoftDepend().isBlank()) {
            builder.append("softdepend: [").append(project.getPluginSettings().getSoftDepend()).append("]\n");
        }
        if (!project.getPluginSettings().getLoadBefore().isBlank()) {
            builder.append("loadbefore: [").append(project.getPluginSettings().getLoadBefore()).append("]\n");
        }
        if (!project.getPluginSettings().getPrefix().isBlank()) {
            builder.append("prefix: \"").append(project.getPluginSettings().getPrefix()).append("\"\n");
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
