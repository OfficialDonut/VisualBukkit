package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.structures.StructCommand;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.maven.shared.invoker.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.slf4j.impl.SimpleLogger;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PluginBuilder {

    private static Map<String, String> resourceCache = new HashMap<>();
    private static BuildWindow buildWindow = new BuildWindow();
    private static Invoker mavenInvoker = new DefaultInvoker();
    private static List<String> mavenGoals = Arrays.asList("clean", "package");

    static {
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");

        String installDir = System.getProperty("install4j.appDir");
        if (installDir != null) {
            mavenInvoker.setMavenHome(new File(installDir, "apache-maven"));
        }
        mavenInvoker.setOutputHandler(string -> {
            if (string != null) {
                Platform.runLater(() -> buildWindow.println(string));
            }
        });
    }

    public static void build(Project project) throws IOException {
        String name = project.getPluginName().replaceAll("\\s", "");
        if (name.isBlank()) {
            name = project.getName() + "Plugin";
        }
        String version = project.getPluginVersion();
        if (version.isBlank()) {
            version = "1.0";
        }
        String packageName = "vb." + name.toLowerCase();

        JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, getClassResource("PluginMain.java"));
        mainClass.setPackage(packageName);

        BuildContext buildContext = new BuildContext(mainClass);
        buildContext.getUtilMethods().forEach(mainClass::addMethod);

        for (BlockCanvas canvas : project.getCanvases()) {
            for (StructureBlock structure : canvas.getStructures()) {
                structure.prepareBuild(buildContext);
            }
        }

        Path buildDir = project.getFolder().resolve("build");
        Path mainDir = buildDir.resolve("src").resolve("main");
        Path packageDir = mainDir.resolve("java").resolve("vb").resolve(name.toLowerCase());
        Path resourcesDir = mainDir.resolve("resources");

        if (Files.exists(buildDir)) {
            MoreFiles.deleteRecursively(buildDir, RecursiveDeleteOption.ALLOW_INSECURE);
        }

        Files.createDirectories(packageDir);
        Files.createDirectories(resourcesDir);

        Files.writeString(buildDir.resolve("pom.xml"), createPom(packageName, name.toLowerCase(), version, buildContext), StandardCharsets.UTF_8);
        Files.writeString(resourcesDir.resolve("plugin.yml"), createYml(project, name, version, mainClass.getQualifiedName()), StandardCharsets.UTF_8);
        Files.writeString(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString(), StandardCharsets.UTF_8);

        String config = project.getPluginConfig();
        if (!config.isEmpty()) {
            MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
            enableMethod.setBody("saveDefaultConfig();" + enableMethod.getBody());
            Files.writeString(resourcesDir.resolve("config.yml"), config, StandardCharsets.UTF_8);
        }

        Set<JavaClassSource> utilClasses = buildContext.getUtilClasses().stream().map(s -> Roaster.parse(JavaClassSource.class, s)).collect(Collectors.toSet());
        for (JavaClassSource utilClass : utilClasses) {
            utilClass.setPackage(packageName);
            Files.writeString(packageDir.resolve(utilClass.getName() + ".java"), utilClass.toString(), StandardCharsets.UTF_8);
        }

        buildWindow.prepare(project, buildDir);
        buildWindow.println("Executing maven tasks...");
        buildWindow.println();
        buildWindow.show();

        new Thread(() -> {
            InvocationRequest request = new DefaultInvocationRequest();
            request.setBaseDirectory(buildDir.toFile());
            request.setGoals(mavenGoals);
            try {
                mavenInvoker.execute(request);
            } catch (Exception e) {
                buildWindow.println(ExceptionUtils.getStackTrace(e));
            }
        }).start();
    }

    private static String createPom(String groupId, String artifactId, String version, BuildContext buildContext) {
        String repositories = buildContext.getMavenRepositories().stream().map(s -> "<repository>\n" + s + "</repository>").collect(Collectors.joining("\n"));
        String dependencies = buildContext.getMavenDependencies().stream().map(s -> "<dependency>\n" + s + "</dependency>").collect(Collectors.joining("\n"));
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <groupId>" + groupId + "</groupId>\n" +
                "    <artifactId>" + artifactId + "</artifactId>\n" +
                "    <version>" + version + "</version>\n" +
                "\n" +
                "    <properties>\n" +
                "        <maven.compiler.source>1.8</maven.compiler.source>\n" +
                "        <maven.compiler.target>1.8</maven.compiler.target>\n" +
                "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                "    </properties>\n" +
                "\n" +
                "    <repositories>\n" +
                "        <repository>\n" +
                "            <id>spigot-repo</id>\n" +
                "            <url>https://hub.spigotmc.org/nexus/content/repositories/snapshots/</url>\n" +
                "        </repository>\n" +
                repositories +
                "    </repositories>\n" +
                "\n" +
                "    <dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>org.spigotmc</groupId>\n" +
                "            <artifactId>spigot-api</artifactId>\n" +
                "            <version>1.16.4-R0.1-SNAPSHOT</version>\n" +
                "            <scope>provided</scope>\n" +
                "        </dependency>\n" +
                dependencies +
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
    }

    private static String createYml(Project project, String pluginName, String version, String mainClassName) {
        StringBuilder pluginYml = new StringBuilder();
        String author = project.getPluginAuthor();
        String desc = project.getPluginDescription();
        String depend = project.getPluginDependencies();
        String softDepend = project.getPluginSoftDepend();
        pluginYml.append("name: ").append(pluginName).append('\n');
        pluginYml.append("version: ").append(version).append('\n');
        pluginYml.append("main: ").append(mainClassName).append('\n');
        if (!author.isBlank()) {
            pluginYml.append("author: ").append(author).append('\n');
        }
        if (!desc.isBlank()) {
            pluginYml.append("description: ").append(desc).append('\n');
        }
        if (!depend.isBlank()) {
            pluginYml.append("depend: [").append(depend).append("]\n");
        }
        if (!softDepend.isBlank()) {
            pluginYml.append("softdepend: [").append(softDepend).append("]\n");
        }
        pluginYml.append("api-version: 1.13\n");
        pluginYml.append("commands:\n");
        for (BlockCanvas canvas : project.getCanvases()) {
            for (StructureBlock structure : canvas.getStructures()) {
                if (structure instanceof StructCommand) {
                    StructCommand command = (StructCommand) structure;
                    if (!command.getName().isBlank()) {
                        pluginYml.append("  ").append(command.getName()).append(":\n");
                        if (!command.getDescription().isEmpty()) {
                            pluginYml.append("    description: ").append(command.getDescription()).append('\n');
                        }
                        if (!command.getAliases().isEmpty()) {
                            pluginYml.append("    aliases: [").append(command.getAliases()).append("]\n");
                        }
                        if (!command.getPermission().isEmpty()) {
                            pluginYml.append("    permission: ").append(command.getPermission()).append('\n');
                        }
                        if (!command.getPermissionMessage().isEmpty()) {
                            pluginYml.append("    permission-message: ").append(command.getPermissionMessage()).append('\n');
                        }
                    }
                }
            }
        }
        return pluginYml.toString();
    }

    public static String getClassResource(String name) throws IOException {
        if (resourceCache.containsKey(name)) {
            return resourceCache.get(name);
        } else {
            try (InputStream inputStream = PluginBuilder.class.getResourceAsStream("/classes/" + name)) {
                String resource = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                resourceCache.put(name, resource);
                return resource;
            }
        }
    }

    private static class BuildWindow extends Stage {

        private TextArea textArea = new TextArea();
        private Button openDirButton = new Button("Open Build Directory");
        private Button rebuildButton = new Button("Rebuild");

        public BuildWindow() {
            textArea.setEditable(false);
            textArea.setWrapText(true);

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> close());

            HBox buttonBox = new CenteredHBox(10, openDirButton, rebuildButton, closeButton);
            buttonBox.setStyle("-fx-border-color: black; -fx-padding: 5;");

            BorderPane rootPane = new BorderPane();
            rootPane.setCenter(textArea);
            rootPane.setBottom(buttonBox);

            Scene scene = new Scene(rootPane, 1000, 600);
            scene.getStylesheets().add("/style.css");

            initOwner(VisualBukkit.getInstance().getPrimaryStage());
            initModality(Modality.APPLICATION_MODAL);
            setTitle("Build Plugin");
            setScene(scene);
        }

        public void prepare(Project project, Path buildDir) {
            textArea.clear();

            openDirButton.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(buildDir.toUri());
                } catch (IOException ex) {
                    NotificationManager.displayException("Failed to open URI", ex);
                }
            });

            rebuildButton.setOnAction(e -> {
                close();
                try {
                    PluginBuilder.build(project);
                } catch (IOException ex) {
                    println(ExceptionUtils.getStackTrace(ex));
                }
            });
        }

        public void print(String string) {
            textArea.appendText(string);
        }

        public void println(String string) {
            textArea.appendText(string + "\n");
        }

        public void println() {
            textArea.appendText("\n");
        }
    }
}
