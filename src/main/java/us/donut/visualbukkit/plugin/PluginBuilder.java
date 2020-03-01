package us.donut.visualbukkit.plugin;

import com.google.common.io.ByteStreams;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.CommandPane;
import us.donut.visualbukkit.editor.Project;
import us.donut.visualbukkit.util.SimpleList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class PluginBuilder {

    private static ClassPool classPool = ClassPool.getDefault();

    public static void init() {
        classPool.importPackage("java.util");
        classPool.importPackage("org.bukkit");
        classPool.importPackage("us.donut.visualbukkit.util");

        try {
            getMainClass();
        } catch (NotFoundException e) {
            VisualBukkit.displayException("Failed to init plugin main class", e);
        }
    }

    public static boolean isCodeValid(BlockPane blockPane) {
        try {
            blockPane.insertInto(getMainClass());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void build(Project project) throws Exception {
        String name = project.getPluginName().replaceAll("\\s", "");
        if (name.isEmpty()) {
            name = "VisualBukkitPlugin";
        }

        CtClass mainClass = getMainClass();

        for (BlockPane blockPane : project.getBlockPanes()) {
            blockPane.insertInto(mainClass);
        }

        Path projectDir = project.getPluginOutputDir().resolve(name);
        Path srcDir = projectDir.resolve("src");
        Path jar = projectDir.resolve(name + ".jar");
        Path pluginYml = srcDir.resolve("plugin.yml");

        if (Files.exists(projectDir)) {
            MoreFiles.deleteRecursively(projectDir, RecursiveDeleteOption.ALLOW_INSECURE);
        }

        Files.createDirectories(srcDir);
        addClasses(srcDir, mainClass, classPool.get(SimpleList.class.getCanonicalName()));
        Files.write(pluginYml, Arrays.asList(createYml(project, name, mainClass.getName()).split("\n")), StandardCharsets.UTF_8);
        createJar(srcDir, jar);
    }

    private static CtClass getMainClass() throws NotFoundException {
        String name = "visualbukkit." + UUID.randomUUID().toString().replace("-", "");
        return classPool.getAndRename(PluginMain.class.getCanonicalName(), name);
    }

    private static String createYml(Project project, String name, String mainClassName) {
        StringBuilder pluginYml = new StringBuilder();
        String ver = project.getPluginVer();
        String author = project.getPluginAuthor();
        String desc = project.getPluginDesc();
        pluginYml.append("name: ").append(name).append('\n');
        pluginYml.append("version: ").append(ver.isEmpty() ? "1.0" : ver).append('\n');
        pluginYml.append("main: ").append(mainClassName).append('\n');
        if (!author.isEmpty()) {
            pluginYml.append("author: ").append(author).append('\n');
        }
        if (!desc.isEmpty()) {
            pluginYml.append("description: ").append(desc).append('\n');
        }
        pluginYml.append("api-version: 1.13\n");
        pluginYml.append("commands:\n");
        for (CommandPane command : project.getCommands()) {
            pluginYml.append("  ").append(command.getCommand()).append(":\n");
        }
        return pluginYml.toString();
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
        } else {
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

    private static void addClasses(Path dir, CtClass... classes) throws IOException, CannotCompileException {
        for (CtClass ctClass : classes) {
            Path packageDir = dir;
            for (String packageComponent : ctClass.getPackageName().split("\\.")) {
                packageDir = packageDir.resolve(packageComponent);
                if (Files.notExists(packageDir)) {
                    Files.createDirectory(packageDir);
                }
            }
            try (OutputStream os = Files.newOutputStream(packageDir.resolve(ctClass.getSimpleName() + ".class"))) {
                os.write(ctClass.toBytecode());
            }
        }
    }
}
