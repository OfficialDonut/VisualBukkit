package us.donut.visualbukkit.plugin;

import com.google.common.io.ByteStreams;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javassist.*;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.BlockInfo;
import us.donut.visualbukkit.blocks.BlockRegistry;
import us.donut.visualbukkit.blocks.CodeBlock;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.CommandPane;
import us.donut.visualbukkit.editor.Project;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.util.SimpleList;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class PluginBuilder {

    private static ClassPool classPool = ClassPool.getDefault();

    public static void init() {
        classPool.importPackage("java.io");
        classPool.importPackage("java.nio.file");
        classPool.importPackage("java.util");
        classPool.importPackage("org.bukkit");
        classPool.importPackage("org.bukkit.block");
        classPool.importPackage("org.bukkit.entity");
        classPool.importPackage("org.bukkit.inventory");
        classPool.importPackage("org.bukkit.inventory.meta");
        classPool.importPackage("org.bukkit.util");
        classPool.importPackage("us.donut.visualbukkit.plugin");
        classPool.importPackage("us.donut.visualbukkit.plugin.modules.classes");
        classPool.importPackage("us.donut.visualbukkit.util");

        try {
            getCtClass(PluginMain.class, null);
        } catch (NotFoundException e) {
            VisualBukkit.displayException("Failed to init plugin main class", e);
        }
    }

    public static boolean isCodeValid(BlockPane blockPane) {
        try {
            CtClass mainClass = getCtClass(PluginMain.class, null);
            insertModules(blockPane.getModules(), mainClass);
            for (CodeBlock block : getBlocksRecursive(blockPane.getBlockArea())) {
                BlockInfo<?> blockInfo = BlockRegistry.getInfo(block);
                insertModules(blockInfo.getModules(), mainClass);
                insertMethods(blockInfo.getUtilMethods(), mainClass);
            }
            blockPane.insertInto(mainClass);
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

        Map<Class<?>, CtClass> classes = new HashMap<>();
        CtClass mainClass = getCtClass(PluginMain.class, null);
        classes.put(PluginMain.class, mainClass);
        classes.put(VariableManager.class, getCtClass(VariableManager.class, mainClass.getPackageName()));
        classes.put(SimpleList.class, getCtClass(SimpleList.class, mainClass.getPackageName()));

        for (CodeBlock block : getBlocksRecursive(project.getBlockPanes())) {
            BlockInfo<?> blockInfo = BlockRegistry.getInfo(block);
            PluginModule[] modules = blockInfo.getModules();
            if (modules != null) {
                for (PluginModule module : modules) {
                    for (Class<?> clazz : module.getClasses()) {
                        classes.put(clazz, getCtClass(clazz, mainClass.getPackageName()));
                    }
                }
                insertModules(modules, mainClass);
            }
            insertMethods(blockInfo.getUtilMethods(), mainClass);
        }

        for (BlockPane blockPane : project.getBlockPanes()) {
            insertModules(blockPane.getModules(), mainClass);
            blockPane.insertInto(mainClass);
        }

        Path outputDir = project.getPluginOutputDir();
        Path srcDir = outputDir.resolve("src");
        Path jar = outputDir.resolve(name + ".jar");
        Path pluginYml = srcDir.resolve("plugin.yml");
        Path configYml = srcDir.resolve("config.yml");

        Files.deleteIfExists(jar);
        if (Files.exists(srcDir)) {
            MoreFiles.deleteRecursively(srcDir, RecursiveDeleteOption.ALLOW_INSECURE);
        }
        Files.createDirectories(srcDir);

        for (CtClass ctClass : classes.values()) {
            for (Map.Entry<Class<?>, CtClass> entry : classes.entrySet()) {
                ctClass.replaceClassName(entry.getKey().getCanonicalName(), entry.getValue().getName());
            }
        }

        addClasses(srcDir, classes.values());
        Files.write(pluginYml, Arrays.asList(createYml(project, name, mainClass.getName()).split("\n")), StandardCharsets.UTF_8);
        Files.write(configYml, Arrays.asList(project.getPluginConfigPane().getConfigContent().split("\n")), StandardCharsets.UTF_8);
        createJar(srcDir, jar);
        MoreFiles.deleteRecursively(srcDir, RecursiveDeleteOption.ALLOW_INSECURE);
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
        for (CommandPane command : project.getCommands()) {
            pluginYml.append("  ").append(command.getCommand()).append(":\n");
            if (!command.getDescription().isEmpty()) {
                pluginYml.append("    description: ").append(command.getDescription()).append('\n');
            }
            if (!command.getAliases().isEmpty()) {
                pluginYml.append("    aliases: [").append(command.getAliases()).append("]\n");
            }
            if (!command.getPermission().isEmpty()) {
                pluginYml.append("    permission: ").append(command.getPermission()).append('\n');
            }
            if (!command.getPermMessage().isEmpty()) {
                pluginYml.append("    permission-message: ").append(command.getPermMessage()).append('\n');
            }
        }
        return pluginYml.toString();
    }

    public static CtClass getCtClass(Class<?> clazz, String packageName) throws NotFoundException {
        if (packageName == null) {
            packageName = "a" + UUID.randomUUID().toString().replace("-", "");
        }
        return classPool.getAndRename(clazz.getCanonicalName(), packageName + "." + clazz.getSimpleName());
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

    private static void addClasses(Path dir, Iterable<CtClass> classes) throws IOException, CannotCompileException {
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

    public static void insertModules(PluginModule[] modules, CtClass mainClass) throws Exception {
        if (modules != null) {
            for (PluginModule module : modules) {
                module.insertInto(mainClass);
            }
        }
    }

    public static void insertMethods(CtMethod[] methods, CtClass mainClass) throws Exception {
        if (methods != null) {
            for (CtMethod method : methods) {
                mainClass.addMethod(new CtMethod(method, mainClass, null));
            }
        }
    }

    public static Set<CodeBlock> getBlocksRecursive(List<BlockPane> panes) {
        Set<CodeBlock> blocks = new HashSet<>();
        for (BlockPane pane : panes) {
            getBlocksRecursive(pane.getBlockArea(), blocks);
        }
        return blocks;
    }

    public static Set<CodeBlock> getBlocksRecursive(Pane pane) {
        Set<CodeBlock> blocks = new HashSet<>();
        getBlocksRecursive(pane, blocks);
        return blocks;
    }

    private static void getBlocksRecursive(Pane pane, Set<CodeBlock> blocks) {
        for (Node child : pane.getChildren()) {
            if (child instanceof CodeBlock) {
                blocks.add((CodeBlock) child);
            }
            if (child instanceof Pane) {
                getBlocksRecursive((Pane) child, blocks);
            }
        }
    }
}
