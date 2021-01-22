package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.BlockCanvas;
import com.gmail.visualbukkit.blocks.CodeBlock;
import com.gmail.visualbukkit.blocks.structures.StructCommand;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.util.CenteredHBox;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.slf4j.impl.SimpleLogger;
import org.zeroturnaround.zip.ZipUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PluginBuilder {

    private static BuildWindow buildWindow = new BuildWindow();
    private static Invoker mavenInvoker = new DefaultInvoker();
    private static List<String> mavenGoals = Arrays.asList("clean", "package");
    private static Map<Class<?>, CtClass> classCache = new HashMap<>();
    private static File javaHome;
    private static final String MAIN_CLASS_SOURCE;

    static {
        System.setProperty(SimpleLogger.LEVEL_IN_BRACKETS_KEY, "true");
        System.setProperty(SimpleLogger.SHOW_LOG_NAME_KEY, "false");
        System.setProperty(SimpleLogger.SHOW_THREAD_NAME_KEY, "false");

        String installDir = System.getProperty("install4j.appDir");
        if (installDir != null) {
            javaHome = new File(installDir, "jre");
            mavenInvoker.setMavenHome(new File(installDir, "apache-maven"));
        }
        mavenInvoker.setOutputHandler(string -> {
            if (string != null) {
                buildWindow.println(string);
            }
        });

        String mainClassSource;
        try (InputStream inputStream = PluginBuilder.class.getResourceAsStream("/PluginMain.java")) {
            mainClassSource = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            mainClassSource = null;
            NotificationManager.displayException("Failed to load main class source", e);
        }
        MAIN_CLASS_SOURCE = mainClassSource;
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void build(Project project) {
        try {
            project.save();
        } catch (IOException ignored) {}
        buildWindow.prepare(project);
        buildWindow.show();
        buildWindow.println("Building plugin...");

        new Thread(() -> {
            String name = project.getPluginName().replaceAll("\\s", "");
            if (StringUtils.isBlank(name)) {
                name = project.getName() + "Plugin";
            }
            String version = project.getPluginVersion();
            if (StringUtils.isBlank(version)) {
                version = "1.0";
            }
            String packageName = "vb.$" + name.toLowerCase();

            Path buildDir = project.getFolder().resolve("build");
            Path mainDir = buildDir.resolve("src").resolve("main");
            Path packageDir = mainDir.resolve("java").resolve("vb").resolve("$" + name.toLowerCase());
            Path resourcesDir = mainDir.resolve("resources");
            Path resourceFilesDir = resourcesDir.resolve("files");

            try {
                buildWindow.println("Generating build directory...");
                if (Files.exists(buildDir)) {
                    MoreFiles.deleteRecursively(buildDir, RecursiveDeleteOption.ALLOW_INSECURE);
                }
                Files.createDirectories(packageDir);
                Files.createDirectories(resourceFilesDir);

                JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, MAIN_CLASS_SOURCE);
                mainClass.setPackage(packageName);

                File[] resourceFiles = project.getResourceFolder().toFile().listFiles();
                if (resourceFiles != null && resourceFiles.length > 0) {
                    buildWindow.println("Copying resource files...");
                    MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
                    for (File file : resourceFiles) {
                        if (file.isFile()) {
                            String fileName = StringEscapeUtils.escapeJava(file.getName());
                            String fileString = "new File(getDataFolder(), \"" + fileName + "\")";
                            if (!fileName.equals("config.yml")) {
                                Files.copy(file.toPath(), resourceFilesDir.resolve(file.getName()));
                                enableMethod.setBody(enableMethod.getBody() +
                                        "if (!" + fileString + ".exists()) {" +
                                        "try {" +
                                        "Files.copy(getClass().getResourceAsStream(\"/files/" + fileName + "\"), " + fileString + ".toPath());" +
                                        "} catch (IOException e) { e.printStackTrace(); }}");
                            } else {
                                Files.copy(file.toPath(), resourcesDir.resolve(file.getName()));
                                enableMethod.setBody(enableMethod.getBody() + "saveDefaultConfig();");
                            }
                        }
                    }
                } else {
                    Files.delete(resourceFilesDir);
                }

                buildWindow.println("Generating source code...");
                BuildContext buildContext = new BuildContext(mainClass);
                for (BlockCanvas canvas : project.getCanvases()) {
                    for (CodeBlock block : canvas.getCodeBlocks()) {
                        prepareBuild(block, buildContext);
                    }
                }
                buildContext.getUtilMethods().forEach(mainClass::addMethod);
                Files.write(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString().getBytes(StandardCharsets.UTF_8));

                buildWindow.println("Generating pom.xml...");
                Files.write(buildDir.resolve("pom.xml"), createPom(name.toLowerCase(), version, buildContext).getBytes(StandardCharsets.UTF_8));

                buildWindow.println("Generating plugin.yml...");
                Files.write(resourcesDir.resolve("plugin.yml"), createYml(project, name, version, mainClass.getQualifiedName()).getBytes(StandardCharsets.UTF_8));

                Set<Class<?>> utilClasses = buildContext.getUtilClasses();
                if (!utilClasses.isEmpty()) {
                    buildWindow.println("Copying utility classes...");
                    Path utilDir = buildDir.resolve("util");
                    Path classesDir = utilDir.resolve("classes");
                    Files.createDirectories(classesDir);
                    for (Class<?> utilClass : utilClasses) {
                        CtClass ctClass = classCache.computeIfAbsent(utilClass, k -> ClassPool.getDefault().getOrNull(utilClass.getName()));
                        if (ctClass != null) {
                            ctClass.writeFile(classesDir.toString());
                        }
                    }
                    FileUtils.copyDirectory(classesDir.toFile(), resourcesDir.toFile());
                    ZipUtil.pack(classesDir.toFile(), utilDir.resolve("util-classes.jar").toFile());
                }

                Set<InputStream> jarInputStreams = buildContext.getJarDependencies();
                if (!jarInputStreams.isEmpty()) {
                    buildWindow.println("Copying dependency jars...");
                    Path dependDir = buildDir.resolve("depend");
                    Files.createDirectories(dependDir);
                    int i = 1;
                    for (InputStream jarInputStream : jarInputStreams) {
                        FileUtils.copyInputStreamToFile(jarInputStream, dependDir.resolve("depend-" + i++ + ".jar").toFile());
                    }
                }

                buildWindow.println("Executing maven tasks...");
                buildWindow.println();

                InvocationRequest request = new DefaultInvocationRequest();
                request.setBaseDirectory(buildDir.toFile());
                request.setGoals(mavenGoals);
                request.setBatchMode(true);
                if (javaHome != null) {
                    request.setJavaHome(javaHome);
                }
                mavenInvoker.execute(request);

            } catch (Exception e) {
                buildWindow.println(ExceptionUtils.getStackTrace(e));
            }
        }).start();
    }

    private static void prepareBuild(Object obj, BuildContext context) {
        if (obj instanceof CodeBlock) {
            ((CodeBlock) obj).prepareBuild(context);
        }
        if (obj instanceof Parent) {
            for (Node child : ((Parent) obj).getChildrenUnmodifiable()) {
                prepareBuild(child, context);
            }
        }
    }

    private static String createPom(String artifactId, String version, BuildContext buildContext) {
        String repositories = buildContext.getMavenRepositories().stream().map(s -> "<repository>\n" + s + "</repository>").collect(Collectors.joining("\n"));
        String dependencies = buildContext.getMavenDependencies().stream().map(s -> "<dependency>\n" + s + "</dependency>").collect(Collectors.joining("\n"));
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <groupId>vb</groupId>\n" +
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
                (buildContext.getUtilClasses().isEmpty() ? "" :
                "        <dependency>\n" +
                "            <groupId>com.gmail.visualbukkit</groupId>\n" +
                "            <artifactId>util</artifactId>\n" +
                "            <version>0</version>\n" +
                "            <scope>system</scope>\n" +
                "            <systemPath>${pom.basedir}/util/util-classes.jar</systemPath>\n" +
                "        </dependency>\n\n") +
                (buildContext.getJarDependencies().isEmpty() ? "" : IntStream.rangeClosed(1, buildContext.getJarDependencies().size()).mapToObj(i ->
                "        <dependency>\n" +
                "            <groupId>com.gmail.visualbukkit</groupId>\n" +
                "            <artifactId>depend-" + i + "</artifactId>\n" +
                "            <version>0</version>\n" +
                "            <scope>system</scope>\n" +
                "            <systemPath>${pom.basedir}/depend/depend-" + i + ".jar</systemPath>\n" +
                "        </dependency>\n\n").collect(Collectors.joining())) +
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
        if (StringUtils.isNotBlank(author)) {
            pluginYml.append("author: ").append(author).append('\n');
        }
        if (StringUtils.isNotBlank(desc)) {
            pluginYml.append("description: ").append(desc).append('\n');
        }
        if (StringUtils.isNotBlank(depend)) {
            pluginYml.append("depend: [").append(depend).append("]\n");
        }
        if (StringUtils.isNotBlank(softDepend)) {
            pluginYml.append("softdepend: [").append(softDepend).append("]\n");
        }
        pluginYml.append("api-version: 1.13\n");
        pluginYml.append("commands:\n");
        for (BlockCanvas canvas : project.getCanvases()) {
            for (CodeBlock block : canvas.getCodeBlocks()) {
                if (block instanceof StructCommand) {
                    StructCommand command = (StructCommand) block;
                    if (StringUtils.isNotBlank(command.getName())) {
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

        public void prepare(Project project) {
            textArea.clear();

            openDirButton.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(project.getFolder().resolve("build").toUri());
                } catch (IOException ex) {
                    NotificationManager.displayException("Failed to open URI", ex);
                }
            });

            rebuildButton.setOnAction(e -> {
                close();
                PluginBuilder.build(project);
            });
        }

        public void println(String string) {
            Platform.runLater(() -> textArea.appendText(string + "\n"));
        }

        public void println() {
            Platform.runLater(() -> textArea.appendText("\n"));
        }
    }
}
