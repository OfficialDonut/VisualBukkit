package com.gmail.visualbukkit.project.maven;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.BuildInfo;
import com.google.common.io.Resources;
import org.apache.maven.shared.invoker.*;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MavenUtil {

    private static final Invoker mavenInvoker = new DefaultInvoker();

    static {
        mavenInvoker.setLogger(new MavenInvokerLogger());
    }

    public static InvocationResult execute(InvocationRequest request) throws MavenInvocationException {
        return mavenInvoker.execute(request);
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

    public static String createPom(String artifactId, String version, BuildInfo buildInfo) throws IOException {
        return Resources.toString(MavenUtil.class.getResource("/plugin/pom.xml"), StandardCharsets.UTF_8)
                .replace("{ARTIFACT_ID}", artifactId)
                .replace("{VERSION}", version)
                .replace("{REPOSITORIES}", buildInfo.getMavenRepositories().stream().map(MavenUtil::getRepositoryString).collect(Collectors.joining("\n        ")))
                .replace("{DEPENDENCIES}", buildInfo.getMavenDependencies().stream().map(MavenUtil::getDependencyString).collect(Collectors.joining("\n        ")));
    }

    public static String getRepositoryString(RemoteRepository repository) {
        return String.format("<repository><id>%s</id><url>%s</url></repository>", repository.getId(), repository.getUrl());
    }

    public static String getDependencyString(Dependency dependency) {
        return "<dependency>" +
                "<groupId>" + dependency.getArtifact().getGroupId() + "</groupId>" +
                "<artifactId>" + dependency.getArtifact().getArtifactId() + "</artifactId>" +
                "<version>" + dependency.getArtifact().getVersion() + "</version>" +
                "<scope>" + dependency.getScope() + "</scope>" +
                "</dependency>";
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
