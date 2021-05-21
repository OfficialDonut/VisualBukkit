package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ExpressionSelector;
import com.gmail.visualbukkit.blocks.StatementSelector;
import com.gmail.visualbukkit.extensions.DefaultBlocksExtension;
import com.gmail.visualbukkit.extensions.ExtensionManager;
import com.gmail.visualbukkit.project.Project;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.ui.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisualBukkitApp extends Application {

    private static String version = VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    private static VisualBukkitLogger logger;
    private static VisualBukkitServer server;
    private static SettingsManager settingsManager;

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Path dataDir = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
    private static Path dataFile = dataDir.resolve("data.json");
    private static JSONObject data;
    private static Timeline autosaveTimeline;
    private static boolean exitNoSave;

    private static BorderPane rootPane = new BorderPane();
    private static SplitPane splitPane = new SplitPane();
    private static Scene scene = new Scene(rootPane, 750, 500);
    private static Stage stage;
    private static StatementSelector statementSelector;
    private static ExpressionSelector expressionSelector;

    @Override
    public void start(Stage stage) throws IOException {
        VisualBukkitApp.stage = stage;
        logger = new VisualBukkitLogger();
        System.setOut(logger.getPrintStream());
        System.setErr(logger.getPrintStream());
        System.out.println("===== Visual Bukkit v" + version + " =====");
        System.out.println("Initializing...");

        DiscordRPC.discordInitialize("799336716027691059", new DiscordEventHandlers(), true);
        DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("Loading...").build());

        if (Files.exists(dataFile)) {
            try {
                data = new JSONObject(Files.readString(dataFile));
            } catch (JSONException e) {
                data = new JSONObject();
            }
        } else {
            data = new JSONObject();
        }

        System.out.println("Loading settings...");
        settingsManager = new SettingsManager();
        settingsManager.bindStyle(logger.getTextArea());
        settingsManager.bindStyle(rootPane);

        statementSelector = new StatementSelector();
        expressionSelector = new ExpressionSelector();

        rootPane.setTop(createMenuBar());
        rootPane.setCenter(splitPane);

        splitPane.widthProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> splitPane.setDividerPositions(0.25)));
        splitPane.getItems().addAll(statementSelector, new Pane());

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.S) {
                e.consume();
                try {
                    saveCurrentProject();
                    NotificationManager.displayMessage(LanguageManager.get("message.saved.title"), LanguageManager.get("message.saved.content"));
                } catch (IOException ex) {
                    NotificationManager.displayException("Failed to save", ex);
                }
            }
        });

        try (InputStream iconInputStream = VisualBukkitApp.class.getResourceAsStream("/images/icon.png");
             InputStream bukkitBlocksInputStream = VisualBukkitApp.class.getResourceAsStream("/blocks/BukkitBlocks.json");
             InputStream javaBlocksInputStream = VisualBukkitApp.class.getResourceAsStream("/blocks/JavaBlocks.json")) {
            stage.getIcons().add(new Image(iconInputStream));
            System.out.println("Loading blocks...");
            JSONArray bukkitBlockArray = new JSONArray(IOUtils.toString(bukkitBlocksInputStream, StandardCharsets.UTF_8));
            JSONArray javaBlockArray = new JSONArray(IOUtils.toString(javaBlocksInputStream, StandardCharsets.UTF_8));
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), bukkitBlockArray, ResourceBundle.getBundle("lang.BukkitBlocks"));
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), javaBlockArray, ResourceBundle.getBundle("lang.JavaBlocks"));
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), "com.gmail.visualbukkit.blocks.definitions", ResourceBundle.getBundle("lang.CustomBlocks"));
        }

        System.out.println("Loading extensions...");
        ExtensionManager.loadExtensions();

        stage.setTitle("Visual Bukkit");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        notifyPreloader(new Preloader.ProgressNotification(1));
        System.out.println("Finished loading.");
        ProjectManager.openLast();

        (server = new VisualBukkitServer()).start().whenComplete((o, e) -> {
            if (e != null) {
                System.out.println("Failed to start the application server.");
                e.printStackTrace();
            } else {
                System.out.println("Successfully started the application server.");
            }
        });

        Platform.runLater(this::isUpdateAvailable);
    }

    @Override
    public void stop() throws IOException {
        System.out.println("Shutting down...");

        if (ProjectManager.getCurrentProject() != null) {
            data.put("last-project", ProjectManager.getCurrentProject().getName());
            if (!exitNoSave) {
                try {
                    ProjectManager.getCurrentProject().save();
                } catch (IOException ignored) {}
            }
        }

        try {
            if (Files.notExists(dataDir)) {
                Files.createDirectories(dataDir);
            }
            Files.writeString(dataFile, data.toString(2));
        } catch (IOException ignored) {}

        if (server != null) {
            server.stop();
        }

        executorService.shutdown();
        DiscordRPC.discordShutdown();
        System.out.println("Finished shut down.");
        logger.writeToFile(dataDir.resolve("log.txt"));
    }

    private MenuBar createMenuBar() {
        return new MenuBar(
                new Menu(LanguageManager.get("menu.file"), null,
                        new ActionMenuItem(LanguageManager.get("menu_item.new_project"), e -> ProjectManager.promptCreateProject(true)),
                        new ActionMenuItem(LanguageManager.get("menu_item.open_project"), e -> ProjectManager.promptOpenProject(true)),
                        new ActionMenuItem(LanguageManager.get("menu_item.rename_project"), e -> ProjectManager.promptRenameProject()),
                        new ActionMenuItem(LanguageManager.get("menu_item.delete_project"), e -> ProjectManager.promptDeleteProject()),
                        new SeparatorMenuItem(),
                        new ActionMenuItem(LanguageManager.get("menu_item.import_project"), e -> ProjectManager.promptImportProject()),
                        new ActionMenuItem(LanguageManager.get("menu_item.import_component"), e -> ProjectManager.getCurrentProject().promptImportComponent()),
                        new ActionMenuItem(LanguageManager.get("menu_item.export_project"), e -> ProjectManager.promptExportProject()),
                        new ActionMenuItem(LanguageManager.get("menu_item.export_component"), e -> ProjectManager.getCurrentProject().promptExportComponent()),
                        new SeparatorMenuItem(),
                        new ActionMenuItem(LanguageManager.get("menu_item.save"), e -> {
                            try {
                                saveCurrentProject();
                                NotificationManager.displayMessage(LanguageManager.get("message.saved.title"), LanguageManager.get("message.saved.content"));
                            } catch (IOException ex) {
                                NotificationManager.displayException("Failed to save project", ex);
                            }
                        }),
                        new ActionMenuItem(LanguageManager.get("menu_item.save_exit"), e -> Platform.exit()),
                        new ActionMenuItem(LanguageManager.get("menu_item.exit_no_save"), e -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LanguageManager.get("dialog.confirm_exit_no_save"));
                            settingsManager.style(alert.getDialogPane());
                            alert.setHeaderText(null);
                            alert.setGraphic(null);
                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == ButtonType.OK) {
                                    exitNoSave = true;
                                    Platform.exit();
                                }
                            });
                        }),
                        new ActionMenuItem(LanguageManager.get("menu_item.check_update"), e -> {
                            if (!isUpdateAvailable()) {
                                NotificationManager.displayMessage(LanguageManager.get("message.no_update.title"), LanguageManager.get("message.no_update.content"));
                            }
                        })),
                new Menu(LanguageManager.get("menu.edit"), null,
                        new ActionMenuItem(LanguageManager.get("menu_item.undo"), e -> UndoManager.undo()),
                        new ActionMenuItem(LanguageManager.get("menu_item.redo"), e -> UndoManager.redo())),
                new Menu(LanguageManager.get("menu.extensions"), null,
                        new ActionMenuItem(LanguageManager.get("menu_item.install_extension"), e -> ExtensionManager.promptInstall()),
                        new ActionMenuItem(LanguageManager.get("menu_item.manage_extensions"), e -> ExtensionManager.openViewer()),
                        new ActionMenuItem(LanguageManager.get("menu_item.extension_help"), e -> openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Extensions")))),
                settingsManager.createMenu(),
                new Menu(LanguageManager.get("menu.help"), null,
                        new ActionMenuItem("Github", e -> openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit"))),
                        new ActionMenuItem("Spigot", e -> openURI(URI.create("https://www.spigotmc.org/resources/visual-bukkit-create-plugins.76474/"))),
                        new ActionMenuItem("Discord", e -> openURI(URI.create("https://discord.gg/ugkvGpu")))));
    }

    public static void saveCurrentProject() throws IOException {
        Project currentProject = ProjectManager.getCurrentProject();
        if (currentProject != null) {
            currentProject.save();
        }
    }

    public static void setAutosaveTime(int minutes) {
        if (autosaveTimeline != null) {
            autosaveTimeline.stop();
        }
        if (minutes > 0) {
            autosaveTimeline = new Timeline(new KeyFrame(Duration.minutes(minutes), e -> {
                try {
                    saveCurrentProject();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }));
            autosaveTimeline.setCycleCount(Timeline.INDEFINITE);
            autosaveTimeline.play();
        } else {
            autosaveTimeline = null;
        }
    }

    public static void openURI(URI uri) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            NotificationManager.displayException("Failed to open URI", e);
        }
    }

    public static void openDirectory(Path dir) {
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                NotificationManager.displayException("Failed to open directory", e);
                return;
            }
        }
        try {
            Desktop.getDesktop().browse(dir.toUri());
        } catch (Exception e1) {
            try {
                Runtime.getRuntime().exec(new String[]{"xdg-open", dir.toUri().toString()});
            } catch (IOException e2) {
                NotificationManager.displayError("Error", "Action not supported by your OS");
            }
        }
    }

    public boolean isUpdateAvailable() {
        try (InputStream inputStream = new URL("https://raw.githubusercontent.com/OfficialDonut/VisualBukkit/master/VB-Application/version").openStream()) {
            String latestVersion = IOUtils.toString(inputStream, StandardCharsets.UTF_8).trim();
            if (version != null && !version.equals(latestVersion)) {
                ButtonType viewButton = new ButtonType(LanguageManager.get("button.view_update"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format(LanguageManager.get("dialog.update.content"), version, latestVersion), viewButton, new ButtonType(LanguageManager.get("button.ignore_update")));
                settingsManager.style(alert.getDialogPane());
                alert.setTitle(LanguageManager.get("dialog.update.title"));
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == viewButton) {
                        openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/releases"));
                    }
                });
                return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    public static VisualBukkitLogger getLogger() {
        return logger;
    }

    public static VisualBukkitServer getServer() {
        return server;
    }

    public static SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static Path getDataDir() {
        return dataDir;
    }

    public static JSONObject getData() {
        return data;
    }

    public static BorderPane getRootPane() {
        return rootPane;
    }

    public static SplitPane getSplitPane() {
        return splitPane;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static StatementSelector getStatementSelector() {
        return statementSelector;
    }

    public static ExpressionSelector getExpressionSelector() {
        return expressionSelector;
    }
}
