package com.gmail.visualbukkit;

import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.LogWindow;
import com.gmail.visualbukkit.ui.ThemesMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.*;

public class VisualBukkitApp extends Application {

    private static final String version = VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    private static final Logger logger = Logger.getLogger("Visual Bukkit");
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.gui");
    private static final Path dataDirectory = Paths.get(System.getProperty("user.home"), "VisualBukkit6_beta"); // todo: remove beta
    private static final Path dataFile = dataDirectory.resolve("data.json");
    private static JSONObject data = new JSONObject();

    private static final BorderPane rootPane = new BorderPane();
    private static final LogWindow logWindow = new LogWindow();
    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        VisualBukkitApp.primaryStage = primaryStage;

        Files.createDirectories(dataDirectory);
        FileHandler logFileHandler = new FileHandler(dataDirectory.resolve("visualbukkit.log").toString(), 10 * 1024 * 1024, 1);
        logFileHandler.setFormatter(new SimpleFormatter());
        logFileHandler.setLevel(Level.ALL);
        logger.addHandler(logFileHandler);
        logger.addHandler(logWindow.getHandler());
        logger.info("Visual Bukkit v" + version);

        DiscordRPC.discordInitialize("799336716027691059", new DiscordEventHandlers(), true);
        DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("Developing plugins").setStartTimestamps(System.currentTimeMillis()).build());

        if (Files.exists(dataFile)) {
            try {
                data = new JSONObject(Files.readString(dataFile));
            } catch (IOException | JSONException e) {
                displayException(e);
            }
        }

        primaryStage.setScene(new Scene(rootPane, 750, 750));
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Visual Bukkit");

        rootPane.setTop(new MenuBar(
                new Menu(localizedText("menu.file"), null,
                        new ActionMenuItem(localizedText("menu.log"), e -> logWindow.show()),
                        new ActionMenuItem(localizedText("menu.restart"), e -> {}),
                        new ActionMenuItem(localizedText("menu.exit"), e -> {})),
                new Menu(localizedText("menu.edit"), null,
                        new ActionMenuItem(localizedText("menu.undo"), e -> UndoManager.undo()),
                        new ActionMenuItem(localizedText("menu.redo"), e -> UndoManager.redo())),
                new Menu(localizedText("menu.settings"), null,
                        new ThemesMenu()),
                new Menu(localizedText("menu.help"), null,
                        new ActionMenuItem("Github", e -> openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit"))),
                        new ActionMenuItem("Spigot", e -> openURI(URI.create("https://www.spigotmc.org/resources/visual-bukkit-create-plugins.76474/"))),
                        new ActionMenuItem("Discord", e -> openURI(URI.create("https://discord.gg/ugkvGpu"))))));

        primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.Z && !(primaryStage.getScene().getFocusOwner() instanceof TextField)) {
                if (e.isShiftDown()) {
                    UndoManager.redo();
                } else {
                    UndoManager.undo();
                }
            }
        });

        primaryStage.show();
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> displayException(e));
        logger.info("Loading initial project");
        Platform.runLater(ProjectManager::openInitial);
    }

    @Override
    public void stop() {
        logger.info("Shutting down");
        if (ProjectManager.getCurrentProject() != null) {
            ProjectManager.getCurrentProject().save();
        }
        try {
            Files.createDirectories(dataDirectory);
            Files.writeString(dataFile, data.toString(2));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save data file", e);
        }
        DiscordRPC.discordShutdown();
    }

    public static String localizedText(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            logger.log(Level.WARNING, "Failed to get text for key: {0}", key);
            return key;
        }
    }

    public static void displayInfo(String message) {
        createNotification(localizedText("notification.info_title"), message).showInformation();
    }

    public static void displayError(String message) {
        createNotification(localizedText("notification.error_title"), message).showError();
    }

    public static void displayException(Throwable e) {
        logger.log(Level.SEVERE, "An unexpected error occurred", e);
        createNotification(localizedText("notification.error_title"), localizedText("notification.unexpected_error"))
                .action(new Action(localizedText("button.show_error"), action -> logWindow.show()))
                .showError();
    }

    private static Notifications createNotification(String title, String text) {
        return Notifications.create()
                .owner(VisualBukkitApp.primaryStage)
                .darkStyle()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(5));
    }

    public static void openURI(URI uri) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            displayException(e);
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Path getDataDirectory() {
        return dataDirectory;
    }

    public static JSONObject getData() {
        return data;
    }

    public static BorderPane getRootPane() {
        return rootPane;
    }

    public static LogWindow getLogWindow() {
        return logWindow;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
