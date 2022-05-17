package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.*;
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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.lang3.SystemUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VisualBukkitApp extends Application {

    private static String version = VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    private static VisualBukkitLogger logger;
    private static VisualBukkitServer server;
    private static SettingsManager settingsManager;

    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Path dataDir = Paths.get(System.getProperty("user.home"), "VisualBukkit5");
    private static Path dataFile = dataDir.resolve("data.json");
    private static JSONObject data;
    private static Timeline autosaveTimeline;
    private static boolean exitNoSave;

    private static BorderPane rootPane = new BorderPane();
    private static SplitPane splitPane = new SplitPane();
    private static TabPane sidePane = new TabPane();
    private static Scene scene = new Scene(rootPane, 750, 500);
    private static Stage stage;
    private static BlockSelector blockSelector;

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

        rootPane.setTop(createMenuBar());
        rootPane.setCenter(splitPane);

        splitPane.widthProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> splitPane.setDividerPositions(0.25)));
        splitPane.getItems().addAll(sidePane, new Pane());

        sidePane.setSide(Side.LEFT);
        sidePane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        sidePane.getTabs().addAll(
                new Tab(LanguageManager.get("label.block_selector"), blockSelector = new BlockSelector()),
                new Tab(LanguageManager.get("label.plugin_settings")));

        sidePane.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof Statement.Block || source instanceof Expression.Block) {
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        sidePane.setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            if (source instanceof Statement.Block || source instanceof Expression.Block) {
                UndoManager.run(source instanceof Statement.Block ?
                        ((Statement.Block) source).getStatementHolder().removeStack((Statement.Block) source) :
                        ((Expression.Block) source).getExpressionParameter().clear());
                e.setDropCompleted(true);
                e.consume();
            }
        });

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
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), new JSONArray(new JSONTokener(bukkitBlocksInputStream)));
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), new JSONArray(new JSONTokener(javaBlocksInputStream)));
            BlockRegistry.register(DefaultBlocksExtension.getInstance(), "com.gmail.visualbukkit.blocks.definitions");
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
                e.printStackTrace();
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
                        new SeparatorMenuItem(),
                        new ActionMenuItem(LanguageManager.get("menu_item.check_update"), e -> {
                            if (!isUpdateAvailable()) {
                                NotificationManager.displayMessage(LanguageManager.get("message.no_update.title"), LanguageManager.get("message.no_update.content"));
                            }
                        }),
                        new ActionMenuItem(LanguageManager.get("menu_item.log"), e -> logger.show())),
                new Menu(LanguageManager.get("menu.edit"), null,
                        new ActionMenuItem(LanguageManager.get("menu_item.undo"), e -> UndoManager.undo()),
                        new ActionMenuItem(LanguageManager.get("menu_item.redo"), e -> UndoManager.redo())),
                new Menu(LanguageManager.get("menu.extensions"), null,
                        new ActionMenuItem(LanguageManager.get("menu_item.manage_extensions"), e -> {
                            openDirectory(ExtensionManager.getExtensionsDir());
                            NotificationManager.displayMessage(LanguageManager.get("message.manage_extensions.title"), LanguageManager.get("message.manage_extensions.content"));
                        }),
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
        try {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
            if (SystemUtils.IS_OS_LINUX) {
                Runtime.getRuntime().exec(new String[]{"xdg-open", dir.toUri().toString()});
            } else {
                Desktop.getDesktop().browse(dir.toUri());
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to open directory", e);
        }
    }

    public boolean isUpdateAvailable() {
        try (InputStream inputStream = new URL("https://api.github.com/repos/OfficialDonut/VisualBukkit/releases/latest").openStream()) {
            JSONObject response = new JSONObject(new JSONTokener(inputStream));
            String latestVersion = response.getString("tag_name");
            if (version != null && !version.equals(latestVersion)) {
                ButtonType viewButton = new ButtonType(LanguageManager.get("button.view_update"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format(LanguageManager.get("dialog.update.content"), version, latestVersion), viewButton, new ButtonType(LanguageManager.get("button.ignore_update")));
                alert.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> alert.getDialogPane().getScene().getWindow().hide());
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
        } catch (Exception ignored) {}
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

    public static TabPane getSidePane() {
        return sidePane;
    }

    public static Scene getScene() {
        return scene;
    }

    public static Stage getStage() {
        return stage;
    }

    public static BlockSelector getBlockSelector() {
        return blockSelector;
    }
}
