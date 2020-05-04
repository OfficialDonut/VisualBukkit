package us.donut.visualbukkit;

import com.sun.javafx.application.LauncherImpl;
import us.donut.visualbukkit.util.DataFile;

import javax.swing.*;
import java.io.IOException;
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
        LauncherImpl.launchApplication(VisualBukkit.class, SplashScreenLoader.class, new String[0]);
    }

    private static void displayError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
