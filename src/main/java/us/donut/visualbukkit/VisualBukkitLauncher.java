package us.donut.visualbukkit;

import com.sun.javafx.application.LauncherImpl;
import us.donut.visualbukkit.util.DataFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VisualBukkitLauncher {

    public static final String VERSION = "v" + VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    public static final Path DATA_FOLDER = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
    public static final DataFile DATA_FILE = new DataFile(DATA_FOLDER.resolve("data.yml"));

    public static void main(String[] args) {
        if (Files.notExists(DATA_FOLDER)) {
            try {
                Files.createDirectory(DATA_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
                displayError("Failed to load data folder");
                return;
            }
        }

        if (System.getProperty("java.version").startsWith("1.8")) {
            try {
                LauncherImpl.launchApplication(VisualBukkit.class, SplashScreenLoader.class, new String[0]);
                return;
            } catch (NoClassDefFoundError ignored) {}
        }

        String javaPath = DATA_FILE.getConfig().getString("java-path");
        if (javaPath != null && !javaPath.startsWith(System.getProperty("java.home"))) {
            try {
                run(javaPath);
            } catch (IOException | URISyntaxException ignored) {}
        }

        int result = JOptionPane.showOptionDialog(
                null,
                "Visual Bukkit must be run with Oracle JRE/JDK 8",
                "Invalid Java Version",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new Object[]{"Select Oracle JRE/JDK 8"},
                null);

        if (result == 0) {
            selectJava();
        }
    }

    private static void selectJava() {
        File dir = new File(System.getProperty("java.home"));
        while (!dir.getName().equalsIgnoreCase("Java")) {
            dir = dir.getParentFile();
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(dir);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File bin = new File(chooser.getSelectedFile(), "bin");
            File javaFile = new File(bin, "java.exe");
            if (!javaFile.exists()) {
                javaFile = new File(bin, "java");
                if (!javaFile.exists()) {
                    displayError("Invalid JRE/JDK directory");
                    selectJava();
                    return;
                }
            }
            String path = javaFile.toPath().toString();
            DATA_FILE.getConfig().set("java-path", path);
            try {
                DATA_FILE.save();
                run(path);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                displayError("An exception occurred");
                selectJava();
            }
        }
    }

    private static void run(String javaPath) throws IOException, URISyntaxException {
        String jarPath = VisualBukkitLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        if (jarPath.charAt(0) == '/') {
            jarPath = jarPath.replaceFirst("/", "");
        }
        ProcessBuilder builder = new ProcessBuilder(javaPath, "-jar", jarPath);
        builder.start();
        System.exit(0);
    }

    private static void displayError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
