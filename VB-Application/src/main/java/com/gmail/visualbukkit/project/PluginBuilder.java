package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.definitions.CompCommand;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.NotificationManager;
import com.google.common.base.Throwables;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.shared.invoker.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.json.JSONObject;
import org.slf4j.impl.SimpleLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PluginBuilder {

    private static AtomicBoolean isBuilding = new AtomicBoolean();
    private static Invoker mavenInvoker = new DefaultInvoker();
    private static Map<String, String> utilClassCache = new HashMap<>();

    static {
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");

        String installDir = System.getProperty("install4j.appDir");
        if (installDir != null) {
            mavenInvoker.setMavenHome(new File(installDir, "apache-maven"));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void build(Project project) {
        if (isBuilding.get()) {
            return;
        }

        try {
            project.save();
        } catch (IOException e) {
            NotificationManager.displayException("Failed to save project", e);
            return;
        }

        isBuilding.set(true);

        System.out.println();
        System.out.println("===============");
        System.out.println("Building Plugin");
        System.out.println("===============");
        System.out.println();

        VisualBukkitApp.getExecutorService().submit(() -> {
            String name = project.getPluginName();
            if (name.isBlank()) {
                name = "Plugin";
            }

            String version = project.getPluginVersion();
            if (version.isBlank()) {
                version = "1.0";
            }

            String packageName = "vb.$" + name.toLowerCase();

            Path buildDir = project.getBuildDir();
            Path mainDir = buildDir.resolve("src").resolve("main");
            Path packageDir = mainDir.resolve("java").resolve("vb").resolve("$" + name.toLowerCase());
            Path resourcesDir = mainDir.resolve("resources");

            try {
                System.out.println("Generating build directory...");
                if (Files.exists(buildDir)) {
                    MoreFiles.deleteRecursively(buildDir, RecursiveDeleteOption.ALLOW_INSECURE);
                }
                Files.createDirectories(packageDir);
                Files.createDirectories(resourcesDir);

                JavaClassSource mainClass = getUtilClass("PluginMain");
                mainClass.setPackage(packageName);

                if (Files.exists(project.getResourcesDir()) && Files.list(project.getResourcesDir()).findAny().isPresent()) {
                    System.out.println("Copying resource files...");
                    for (Path path : Files.walk(project.getResourcesDir()).toArray(Path[]::new)) {
                        if (Files.isRegularFile(path) && !Files.isHidden(path)) {
                            Path relativePath = project.getResourcesDir().relativize(path);
                            Path resourceDirPath = resourcesDir.resolve(relativePath);
                            Files.createDirectories(resourceDirPath.getParent());
                            Files.copy(path, resourceDirPath);
                            String filePath = StringEscapeUtils.escapeJava(relativePath.toString().replace("\\", "/"));
                            MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
                            enableMethod.setBody(enableMethod.getBody() + (filePath.equals("config.yml") ? "saveDefaultConfig();" : ("PluginMain.createResourceFile(\"" + filePath + "\");")));
                        }
                    }
                }

                System.out.println("Generating source code...");
                BuildContext buildContext = new BuildContext(mainClass);
                project.getPluginComponents().forEach(component -> component.prepareBuild(buildContext));
                buildContext.getUtilMethods().forEach(mainClass::addMethod);
                Files.writeString(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString());

                System.out.println("Generating pom.xml...");
                Files.writeString(buildDir.resolve("pom.xml"), createPom(name.toLowerCase(), version, buildContext));

                System.out.println("Generating plugin.yml...");
                Files.writeString(resourcesDir.resolve("plugin.yml"), createYml(project, name, version, mainClass.getQualifiedName()));

                Set<JavaClassSource> utilClasses = buildContext.getUtilClasses();
                if (!utilClasses.isEmpty()) {
                    System.out.println("Copying utility classes...");
                    for (JavaClassSource utilClass : utilClasses) {
                        utilClass.setPackage(packageName);
                        Files.writeString(packageDir.resolve(utilClass.getName() + ".java"), utilClass.toString());
                    }
                }

                Set<InputStream> jarInputStreams = buildContext.getJarDependencies();
                if (!jarInputStreams.isEmpty()) {
                    System.out.println("Copying dependency jars...");
                    Path dependDir = buildDir.resolve("depend");
                    Files.createDirectories(dependDir);
                    int i = 1;
                    for (InputStream jarInputStream : jarInputStreams) {
                        FileUtils.copyInputStreamToFile(jarInputStream, dependDir.resolve("depend-" + i++ + ".jar").toFile());
                        jarInputStream.close();
                    }
                }

                System.out.println("Executing maven tasks...");
                System.out.println();

                InvocationRequest request = new DefaultInvocationRequest();
                request.setBaseDirectory(buildDir.toFile());
                request.setGoals(Arrays.asList("clean", "package"));
                request.setBatchMode(true);

                InvocationResult result = mavenInvoker.execute(request);
                if (result.getExecutionException() != null) {
                    result.getExecutionException().printStackTrace();
                } else if (result.getExitCode() == 0) {
                    try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(buildDir.resolve("target"))) {
                        for (Path path : dirStream) {
                            if (path.getFileName().toString().startsWith("original-")) {
                                Files.delete(path);
                                break;
                            }
                        }
                    }
                    System.out.println();
                    System.out.println("================================================================");
                    System.out.println("Click the 'Open Build Directory' button to access the plugin jar");
                    System.out.println("================================================================");
                }

            } catch (Exception e) {
                System.out.println(Throwables.getStackTraceAsString(e));
            }

            isBuilding.set(false);
        });
    }

    public static void deploy(Project project) {
        if (!VisualBukkitApp.getServer().isClientConnected()) {
            NotificationManager.displayError(LanguageManager.get("error.server_not_connected.title"), LanguageManager.get("error.server_not_connected.content"));
            return;
        }

        Path targetDir = project.getBuildDir().resolve("target");
        if (Files.exists(targetDir)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(targetDir)) {
                for (Path path : dirStream) {
                    if (path.getFileName().toString().endsWith(".jar")) {
                        JSONObject json = new JSONObject();
                        json.put("id", "deploy");
                        json.put("jar-path", path.toString());
                        VisualBukkitApp.getServer().send(json);
                        NotificationManager.displayMessage(LanguageManager.get("message.deployed_plugin.title"), LanguageManager.get("message.deployed_plugin.content"));
                        return;
                    }
                }
            } catch (IOException e) {
                NotificationManager.displayException("Failed to deploy plugin", e);
                return;
            }
        }

        NotificationManager.displayError(LanguageManager.get("error.cannot_deploy_plugin.title"), LanguageManager.get("error.cannot_deploy_plugin.content"));
    }

    private static String createPom(String artifactId, String version, BuildContext buildContext) {
        return POM_STRING
                .replace("{ARTIFACT_ID}", artifactId)
                .replace("{VERSION}", version)
                .replace("{REPOSITORIES}", buildContext.getMavenRepositories().stream().map(s -> "<repository>\n" + s + "</repository>").collect(Collectors.joining("\n")))
                .replace("{DEPENDENCIES}",
                        buildContext.getMavenDependencies().stream().map(s -> "<dependency>\n" + s + "</dependency>\n").collect(Collectors.joining()) +
                        IntStream.rangeClosed(1, buildContext.getJarDependencies().size()).mapToObj(i ->
                        "        <dependency>\n" +
                        "            <groupId>com.gmail.visualbukkit</groupId>\n" +
                        "            <artifactId>depend-" + i + "</artifactId>\n" +
                        "            <version>0</version>\n" +
                        "            <scope>system</scope>\n" +
                        "            <systemPath>${pom.basedir}/depend/depend-" + i + ".jar</systemPath>\n" +
                        "        </dependency>\n").collect(Collectors.joining()));
    }

    private static String createYml(Project project, String pluginName, String version, String mainClassName) {
        StringBuilder pluginYml = new StringBuilder(YML_STRING.replace("{NAME}", pluginName).replace("{VERSION}", version).replace("{MAIN_CLASS}", mainClassName));
        StringBuilder commandsBuilder = new StringBuilder("commands:\n");
        StringBuilder permissionsBuilder = new StringBuilder("permissions:\n");
        if (!project.getPluginAuthor().isBlank()) {
            pluginYml.append("author: \"").append(project.getPluginAuthor()).append("\"\n");
        }
        if (!project.getPluginDescription().isBlank()) {
            pluginYml.append("description: \"").append(project.getPluginDescription()).append("\"\n");
        }
        if (!project.getPluginWebsite().isBlank()) {
            pluginYml.append("website: \"").append(project.getPluginWebsite()).append("\"\n");
        }
        if (!project.getPluginDependencies().isBlank()) {
            pluginYml.append("depend: [").append(project.getPluginDependencies()).append("]\n");
        }
        if (!project.getPluginSoftDependencies().isBlank()) {
            pluginYml.append("softdepend: [").append(project.getPluginSoftDependencies()).append("]\n");
        }
        if (!project.getPluginPermissions().isBlank()) {
            permissionsBuilder.append("  ").append(project.getPluginPermissions().replace("\n", "\n  ")).append("\n");
        }
        for (PluginComponent.Block pluginComponent : project.getPluginComponents()) {
            if (pluginComponent.getDefinition() instanceof CompCommand && !pluginComponent.arg(0).isBlank()) {
                commandsBuilder.append("  ").append(pluginComponent.arg(0)).append(":\n");
                if (!pluginComponent.arg(1).isBlank()) {
                    commandsBuilder.append("    aliases: [").append(pluginComponent.arg(1)).append("]\n");
                }
                if (!pluginComponent.arg(2).isBlank()) {
                    commandsBuilder.append("    description: \"").append(pluginComponent.arg(2)).append("\"\n");
                }
                if (!pluginComponent.arg(3).isBlank()) {
                    commandsBuilder.append("    permission: \"").append(pluginComponent.arg(3)).append("\"\n");
                    permissionsBuilder.append("  ").append(pluginComponent.arg(3)).append(":\n");
                    permissionsBuilder.append("    default: op\n");
                }
                if (!pluginComponent.arg(4).isBlank()) {
                    commandsBuilder.append("    permission-message: \"").append(pluginComponent.arg(4)).append("\"\n");
                }
                if (!pluginComponent.arg(5).isBlank()) {
                    commandsBuilder.append("    usage: \"").append(pluginComponent.arg(5)).append("\"\n");
                }
            }
        }
        return pluginYml.append(commandsBuilder).append(permissionsBuilder).toString();
    }

    protected static JavaClassSource getUtilClass(String clazz) {
        return Roaster.parse(JavaClassSource.class, utilClassCache.computeIfAbsent(clazz, k -> {
            try (InputStream inputStream = PluginBuilder.class.getResourceAsStream("/classes/" + k + ".java")) {
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                return null;
            }
        }));
    }

    public static String POM_STRING =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <modelVersion>4.0.0</modelVersion>\n" +
            "\n" +
            "    <groupId>vb</groupId>\n" +
            "    <artifactId>{ARTIFACT_ID}</artifactId>\n" +
            "    <version>{VERSION}</version>\n" +
            "\n" +
            "    <properties>\n" +
            "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
            "        <maven.compiler.source>1.8</maven.compiler.source>\n" +
            "        <maven.compiler.target>1.8</maven.compiler.target>\n" +
            "    </properties>\n" +
            "\n" +
            "    <repositories>\n" +
            "        <repository>\n" +
            "            <id>spigot-repo</id>\n" +
            "            <url>https://hub.spigotmc.org/nexus/content/repositories/snapshots/</url>\n" +
            "        </repository>\n" +
            "        <repository>\n" +
            "           <id>jitpack.io</id>\n" +
            "           <url>https://jitpack.io</url>\n" +
            "        </repository>\n" +
            "{REPOSITORIES}" +
            "    </repositories>\n" +
            "\n" +
            "    <dependencies>\n" +
            "        <dependency>\n" +
            "            <groupId>org.spigotmc</groupId>\n" +
            "            <artifactId>spigot-api</artifactId>\n" +
            "            <version>1.17-R0.1-SNAPSHOT</version>\n" +
            "            <scope>provided</scope>\n" +
            "        </dependency>\n" +
            "{DEPENDENCIES}" +
            "    </dependencies>\n" +
            "\n" +
            "    <build>\n" +
            "        <plugins>\n" +
            "            <plugin>\n" +
            "                <artifactId>maven-compiler-plugin</artifactId>\n" +
            "                <version>3.8.0</version>\n" +
            "                <configuration>\n" +
            "                    <compilerId>eclipse</compilerId>\n" +
            "                </configuration>\n" +
            "                <dependencies>\n" +
            "                    <dependency>\n" +
            "                        <groupId>org.codehaus.plexus</groupId>\n" +
            "                        <artifactId>plexus-compiler-eclipse</artifactId>\n" +
            "                        <version>2.8.8</version>\n" +
            "                    </dependency>\n" +
            "                </dependencies>\n" +
            "            </plugin>\n" +
            "\n" +
            "            <plugin>\n" +
            "                <groupId>org.apache.maven.plugins</groupId>\n" +
            "                <artifactId>maven-shade-plugin</artifactId>\n" +
            "                <version>3.2.1</version>\n" +
            "                <executions>\n" +
            "                    <execution>\n" +
            "                        <phase>package</phase>\n" +
            "                        <goals>\n" +
            "                            <goal>shade</goal>\n" +
            "                        </goals>\n" +
            "                    </execution>\n" +
            "                </executions>\n" +
            "            </plugin>\n" +
            "        </plugins>\n" +
            "    </build>" +
            "\n" +
            "</project>";

    public static String YML_STRING =
            "name: \"{NAME}\"\n" +
            "version: \"{VERSION}\"\n" +
            "main: \"{MAIN_CLASS}\"\n" +
            "api-version: 1.13\n";
}
