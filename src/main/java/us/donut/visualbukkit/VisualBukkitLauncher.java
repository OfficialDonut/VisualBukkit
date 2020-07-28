package us.donut.visualbukkit;

import com.sun.javafx.application.LauncherImpl;
import org.apache.commons.lang.exception.ExceptionUtils;
import us.donut.visualbukkit.util.DataFile;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VisualBukkitLauncher {

    public static final String VERSION = "v" + VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    public static final Path DATA_FOLDER = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
    public static final DataFile DATA_FILE = new DataFile(DATA_FOLDER.resolve("data.yml"));
    public static final Logger LOGGER = Logger.getLogger("VisualBukkit");

    public static void main(String[] args)  {
        try {
            if (Files.notExists(DATA_FOLDER)) {
                Files.createDirectory(DATA_FOLDER);
            }
            FileHandler fileHandler = new FileHandler(DATA_FOLDER.resolve("log.txt").toString(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LauncherImpl.launchApplication(VisualBukkit.class, SplashScreenLoader.class, new String[0]);
        } catch (Exception e) {
            LOGGER.severe(ExceptionUtils.getStackTrace(e));
            JOptionPane.showMessageDialog(null, "Failed to launch Visual Bukkit", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
