package us.donut.visualbukkit.plugin;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javassist.*;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.structures.StructCommand;
import us.donut.visualbukkit.editor.BlockCanvas;
import us.donut.visualbukkit.editor.Project;
import us.donut.visualbukkit.plugin.modules.PluginModule;

import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

@SuppressWarnings("UnstableApiUsage")
public class PluginBuilder {

    private static EclipseCompiler compiler = new EclipseCompiler();
    private static String mainClassSource;

    static  {
        try (Reader reader = new InputStreamReader(PluginBuilder.class.getResourceAsStream("/PluginMain.java"), StandardCharsets.UTF_8)) {
            mainClassSource = CharStreams.toString(reader);
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to load resource", e);
            Platform.exit();
        }
    }

    public static void build(Project project) throws IOException, CannotCompileException, NotFoundException {
        String name = project.getPluginName().replaceAll("\\s", "");
        if (name.isEmpty()) {
            name = project.getName() + "Plugin";
        }
        String packageName = "a" + UUID.randomUUID().toString().replace("-", "");

        JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, mainClassSource);
        mainClass.setPackage(packageName);

        Map<Class<?>, CtClass> classes = new HashMap<>();
        BuildContext.create();

        for (BlockCanvas canvas : project.getCanvases()) {
            for (StructureBlock structure : canvas.getStructures()) {
                structure.insertInto(mainClass);
            }
        }

        for (PluginModule module : BuildContext.getPluginModules()) {
            module.insertInto(mainClass);
            for (Class<?> clazz : module.getClasses()) {
                classes.put(clazz, getCtClass(clazz, packageName));
            }
        }

        for (CtClass ctClass : classes.values()) {
            for (Map.Entry<Class<?>, CtClass> entry : classes.entrySet()) {
                ctClass.replaceClassName(entry.getKey().getName(), entry.getValue().getName());
            }
        }

        CtClass utilMethodsClass = getCtClass(UtilMethods.class, packageName);
        for (CtMethod method : utilMethodsClass.getDeclaredMethods()) {
            if (!BuildContext.getUtilMethods().contains(method.getName())) {
                utilMethodsClass.removeMethod(method);
            }
        }
        if (utilMethodsClass.getDeclaredMethods().length > 0) {
            classes.put(UtilMethods.class, utilMethodsClass);
        }

        Path outputDir = project.getPluginOutputDir();
        Path srcDir = outputDir.resolve("src");
        Path packageDir = srcDir.resolve(packageName);
        Path jar = outputDir.resolve(name + ".jar");

        Files.deleteIfExists(jar);
        if (Files.exists(srcDir)) {
            MoreFiles.deleteRecursively(srcDir, RecursiveDeleteOption.ALLOW_INSECURE);
        }
        Files.createDirectories(packageDir);

        String config = project.getPluginConfig();
        if (!config.isEmpty()) {
            MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
            enableMethod.setBody("saveDefaultConfig();" + enableMethod.getBody());
            Files.write(srcDir.resolve("config.yml"), config.getBytes(StandardCharsets.UTF_8));
        }

        for (CtClass ctClass : classes.values()) {
            ctClass.writeFile(srcDir.toString());
        }

        Files.write(packageDir.resolve("PluginMain.java"), mainClass.toString().getBytes(StandardCharsets.UTF_8));
        Files.write(srcDir.resolve("plugin.yml"), createYml(project, name, mainClass.getQualifiedName()).getBytes(StandardCharsets.UTF_8));

        String compileResult = compile(packageDir);
        if (compileResult.isEmpty()) {
            createJar(srcDir, jar);
            VisualBukkit.displayMessage("Built plugin", "Successfully built plugin\n(" + jar.toString() + ")");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);
            alert.setHeaderText(null);
            alert.setGraphic(null);
            VBox content = new VBox(5, new Text("Failed to build plugin"), new TextArea(compileResult));
            alert.getDialogPane().setContent(content);
            alert.showAndWait();
        }
    }

    public static String createYml(Project project, String name, String mainClassName) {
        StringBuilder pluginYml = new StringBuilder();
        String ver = project.getPluginVer();
        String author = project.getPluginAuthor();
        String desc = project.getPluginDesc();
        String website = project.getPluginWebsite();
        String depend = project.getPluginDepend();
        String softDepend = project.getPluginSoftDepend();
        pluginYml.append("name: ").append(name).append('\n');
        pluginYml.append("version: ").append(ver.isEmpty() ? "1.0" : ver).append('\n');
        pluginYml.append("main: ").append(mainClassName).append('\n');
        if (!author.isEmpty()) {
            pluginYml.append("author: ").append(author).append('\n');
        }
        if (!desc.isEmpty()) {
            pluginYml.append("description: ").append(desc).append('\n');
        }
        if (!website.isEmpty()) {
            pluginYml.append("website: ").append(website).append('\n');
        }
        if (!depend.isEmpty()) {
            pluginYml.append("depend: [").append(depend).append("]\n");
        }
        if (!softDepend.isEmpty()) {
            pluginYml.append("softdepend: [").append(softDepend).append("]\n");
        }
        pluginYml.append("api-version: 1.13\n");
        pluginYml.append("commands:\n");
        for (BlockCanvas canvas : project.getCanvases()) {
            for (StructureBlock structure : canvas.getStructures()) {
                if (structure instanceof StructCommand) {
                    StructCommand command = (StructCommand) structure;
                    if (!command.getName().isEmpty()) {
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
                    }
                }
            }
        }
        return pluginYml.toString();
    }

    private static String compile(Path packageDir) {
        StringJoiner errorStringJoiner = new StringJoiner("\n");
        DiagnosticListener<JavaFileObject> listener = (diagnostic) -> errorStringJoiner.add(
                diagnostic.getMessage(Locale.getDefault()) +
                " (" + new File(diagnostic.getSource().getName()).getName() + " " + diagnostic.getLineNumber() + ")");
        StandardJavaFileManager manager = compiler.getStandardFileManager(listener, Locale.getDefault(), StandardCharsets.UTF_8);
        Iterable<? extends JavaFileObject> files = manager.getJavaFileObjects(packageDir.toFile().listFiles(file -> file.getName().endsWith(".java")));
        List<String> options = Arrays.asList("-cp", ".;" + packageDir.getParent(), "-source", "1.8", "-target", "1.8", "-nowarn");
        compiler.getTask(null, manager, listener, options, null, files).call();
        return errorStringJoiner.toString();
    }

    private static void createJar(Path rootDir, Path jar) throws IOException {
        try (OutputStream os = Files.newOutputStream(jar);
             JarOutputStream jos = new JarOutputStream(os);
             DirectoryStream<Path> paths = Files.newDirectoryStream(rootDir)) {
            for (Path path : paths) {
                addJarEntries(jos, path, path);
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private static void addJarEntries(JarOutputStream jos, Path rootDir, Path file) throws IOException {
        if (Files.isDirectory(file)) {
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(file)) {
                for (Path path : paths) {
                    addJarEntries(jos, rootDir, path);
                }
            }
        } else if (!file.getFileName().toString().endsWith(".java")) {
            try (InputStream is = Files.newInputStream(file)) {
                JarEntry jarEntry = new JarEntry(file.toString()
                        .replace(rootDir.getParent() + File.separator, "")
                        .replace("\\", "/"));
                jos.putNextEntry(jarEntry);
                jos.write(ByteStreams.toByteArray(is));
                jos.closeEntry();
            }
        }
    }

    private static CtClass getCtClass(Class<?> clazz, String packageName) throws NotFoundException {
        String className = packageName + "." + (clazz.isAnonymousClass() ?
                clazz.getName().replaceFirst(clazz.getPackage().getName() + "\\.", "") :
                clazz.getSimpleName());
        return ClassPool.getDefault().getAndRename(clazz.getName(), className);
    }
}
