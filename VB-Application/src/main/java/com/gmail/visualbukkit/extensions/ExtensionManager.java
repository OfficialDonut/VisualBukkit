package com.gmail.visualbukkit.extensions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.NotificationManager;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarFile;

public class ExtensionManager {

    private static Path extensionsDir = VisualBukkitApp.getDataDir().resolve("Extensions");
    private static Map<String, VisualBukkitExtension> extensionMap = new TreeMap<>();

    public static void loadExtensions() throws IOException {
        Files.createDirectories(extensionsDir);

        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(extensionsDir)) {
            for (Path path : dirStream) {
                if (path.toString().endsWith(".jar")) {
                    try (JarFile jarFile = new JarFile(path.toFile())) {
                        String mainClassName = jarFile.getManifest().getMainAttributes().getValue("main-class");
                        if (mainClassName != null) {
                            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{path.toUri().toURL()})) {
                                Class<?> mainClass = Class.forName(mainClassName, true, classLoader);
                                if (VisualBukkitExtension.class.isAssignableFrom(mainClass)) {
                                    VisualBukkitExtension extension = (VisualBukkitExtension) mainClass.getConstructor().newInstance();
                                    extensionMap.put(extension.getName(), extension);
                                    System.out.println("Loaded extension: " + extension.getName() + " " + extension.getVersion());
                                }
                            }
                        }
                    } catch (Throwable e) {
                        NotificationManager.displayException("Failed to load extension: " + path.getFileName(), e);
                    }
                }
            }
        }
    }

    public static VisualBukkitExtension getExtension(String name) {
        return extensionMap.get(name);
    }

    public static Collection<VisualBukkitExtension> getExtensions() {
        return Collections.unmodifiableCollection(extensionMap.values());
    }

    public static Path getExtensionsDir() {
        return extensionsDir;
    }
}
