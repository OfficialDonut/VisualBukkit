package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.TypeHandler;
import com.gmail.visualbukkit.blocks.UndoManager;
import com.gmail.visualbukkit.extensions.ExtensionManager;
import com.gmail.visualbukkit.gui.IconButton;
import com.gmail.visualbukkit.gui.LogDisplay;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.gui.StatementSelector;
import com.gmail.visualbukkit.plugin.PluginBuilder;
import com.gmail.visualbukkit.plugin.Project;
import com.gmail.visualbukkit.plugin.ProjectManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.json.JSONArray;

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

public class VisualBukkitApp extends Application {

    public static final String VERSION = String.valueOf(VisualBukkitLauncher.class.getPackage().getSpecificationVersion());
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("lang.GUI");
    private static VisualBukkitApp instance;

    private boolean saveOnExit = true;
    private Path dataDir;
    private DataFile dataFile;

    private BorderPane rootPane = new BorderPane();
    private BorderPane innerBorderPane = new BorderPane();
    private Scene scene = new Scene(rootPane, 500, 500);
    private Stage primaryStage;
    private StatementSelector statementSelector;
    private LogDisplay logDisplay;

    public VisualBukkitApp() {
        if (instance != null) {
            throw new IllegalStateException();
        }
        instance = this;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        dataDir = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
        dataFile = new DataFile(dataDir.resolve("data.json"));
        SettingsManager.getInstance().loadSettings(dataFile);
        logDisplay = new LogDisplay();
        System.setOut(logDisplay.getPrintStream());
        System.setErr(logDisplay.getPrintStream());
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> Platform.runLater(() -> {
            NotificationManager.displayException("An exception occurred", e);
            if (!stage.isShowing()) {
                Platform.exit();
            }
        }));
        System.out.println("===== Visual Bukkit v" + VERSION + " =====");
        DiscordRPC.discordInitialize("799336716027691059", new DiscordEventHandlers(), true);
        DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("Loading...").build());
        Platform.runLater(this::load);
    }

    @Override
    public void stop() throws IOException {
        NotificationManager.log("Shutting down...");
        dataFile.clear();
        SettingsManager.getInstance().saveSettings(dataFile);
        if (ProjectManager.getCurrentProject() != null) {
            dataFile.getJson().put("last-project", ProjectManager.getCurrentProject().getDir().getFileName());
        }
        if (statementSelector != null) {
            statementSelector.savePinned(dataFile);
        }
        dataFile.save();
        if (saveOnExit) {
            saveCurrentProject();
        }
        DiscordRPC.discordShutdown();
        NotificationManager.log("Finished shut down.");
        logDisplay.writeToFile(dataDir.resolve("log.txt"));
    }

    private void load() {
        NotificationManager.log("Loading...");
        Stage splashScreen = new Stage();
        VBox splashRootPane = new VBox();
        splashRootPane.getStyleClass().add("splash-screen");
        SettingsManager.getInstance().bindStyle(splashRootPane);

        try (InputStream inputStream = VisualBukkitApp.class.getResourceAsStream("/images/icon.png")) {
            Image icon = new Image(inputStream);
            primaryStage.getIcons().add(icon);
            splashScreen.getIcons().add(icon);
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            splashRootPane.getChildren().addAll(new Label("Visual Bukkit"), imageView);
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load icon", e);
        }

        splashScreen.setTitle("Visual Bukkit");
        splashScreen.setScene(new Scene(splashRootPane, 600, 500));
        splashScreen.initStyle(StageStyle.UNDECORATED);
        splashScreen.show();

        Platform.runLater(() -> {
            try (InputStream javaBlocksStream = VisualBukkitApp.class.getResourceAsStream("/blocks/JavaBlocks.json");
                 InputStream bukkitBlocksStream = VisualBukkitApp.class.getResourceAsStream("/blocks/BukkitBlocks.json")) {
                TypeHandler.registerClassNames(ResourceBundle.getBundle("lang.Types"));
                JSONArray javaBlockArray = new JSONArray(IOUtils.toString(javaBlocksStream, StandardCharsets.UTF_8));
                JSONArray bukkitBlockArray = new JSONArray(IOUtils.toString(bukkitBlocksStream, StandardCharsets.UTF_8));
                BlockRegistry.register(javaBlockArray, ResourceBundle.getBundle("lang.JavaBlocks"));
                BlockRegistry.register(bukkitBlockArray, ResourceBundle.getBundle("lang.BukkitBlocks"));
                BlockRegistry.register("com.gmail.visualbukkit.blocks.definitions", VisualBukkitApp.class.getClassLoader(), ResourceBundle.getBundle("lang.CustomBlocks"));
                ExtensionManager.loadExtensions();
                statementSelector = new StatementSelector(BlockRegistry.getStatements());
            } catch (IOException e) {
                NotificationManager.displayException("Failed to load blocks", e);
                Platform.exit();
            }

            AutoSaver.getInstance().setTime(SettingsManager.getInstance().getAutosaveTime());
            scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if (e.isShortcutDown() && e.getCode() == KeyCode.S) {
                    e.consume();
                    try {
                        saveCurrentProject();
                        NotificationManager.displayMessage(getString("message.saved.title"), getString("message.saved.content"));
                    } catch (IOException ex) {
                        NotificationManager.displayException("Failed to save", ex);
                    }
                }
            });

            SplitPane splitPane = new SplitPane(statementSelector, innerBorderPane);
            rootPane.setCenter(splitPane);
            rootPane.setTop(loadMenuBar());
            innerBorderPane.setBottom(loadButtonBar());

            primaryStage.setTitle("Visual Bukkit");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            SettingsManager.getInstance().bindStyle(rootPane);
            splashScreen.close();
            primaryStage.show();
            splitPane.setDividerPosition(0, 0.25);
            NotificationManager.log("Finished loading.");
            ProjectManager.openLast();
            Platform.runLater(this::checkForUpdate);
        });
    }

    private MenuBar loadMenuBar() {
        Menu fileMenu = new Menu(getString("menu.file"));
        MenuItem newItem = new MenuItem(getString("menu_item.new_project"));
        MenuItem openItem = new MenuItem(getString("menu_item.open_project"));
        MenuItem renameItem = new MenuItem(getString("menu_item.rename_project"));
        MenuItem deleteItem = new MenuItem(getString("menu_item.delete_project"));
        MenuItem importProjectItem = new MenuItem(getString("menu_item.import_project"));
        MenuItem importComponentItem = new MenuItem(getString("menu_item.import_component"));
        MenuItem exportProjectItem = new MenuItem(getString("menu_item.export_project"));
        MenuItem exportComponentItem = new MenuItem(getString("menu_item.export_component"));
        MenuItem saveItem = new MenuItem(getString("menu_item.save"));
        MenuItem saveAndExitItem = new MenuItem(getString("menu_item.save_exit"));
        MenuItem exitNoSaveItem = new MenuItem(getString("menu_item.exit_no_save"));
        MenuItem updateItem = new MenuItem(getString("menu_item.check_update"));
        newItem.setOnAction(e -> ProjectManager.promptCreateProject(true));
        openItem.setOnAction(e -> ProjectManager.promptOpenProject(true));
        renameItem.setOnAction(e -> ProjectManager.promptRenameProject());
        deleteItem.setOnAction(e -> ProjectManager.promptDeleteProject());
        importProjectItem.setOnAction(e -> ProjectManager.promptImportProject());
        importComponentItem.setOnAction(e -> ProjectManager.getCurrentProject().promptImportComponent());
        exportProjectItem.setOnAction(e -> ProjectManager.promptExportProject());
        exportComponentItem.setOnAction(e -> ProjectManager.getCurrentProject().promptExportComponent());
        saveItem.setOnAction(e -> {
            try {
                saveCurrentProject();
            } catch (IOException ex) {
                NotificationManager.displayException("Failed to save", ex);
            }
        });
        saveAndExitItem.setOnAction(e -> Platform.exit());
        exitNoSaveItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, getString("dialog.confirm_exit_no_save"));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    saveOnExit = false;
                    Platform.exit();
                }
            });
        });
        updateItem.setOnAction(e -> {
            if (!checkForUpdate()) {
                NotificationManager.displayMessage(getString("message.no_update.content"), getString("message.no_update.title"));
            }
        });
        fileMenu.getItems().addAll(
                newItem, openItem, renameItem, deleteItem, new SeparatorMenuItem(),
                importProjectItem, importComponentItem, exportProjectItem, exportComponentItem, new SeparatorMenuItem(),
                saveItem, saveAndExitItem, exitNoSaveItem, new SeparatorMenuItem(),
                updateItem);

        Menu editMenu = new Menu(getString("menu.edit"));
        MenuItem undoItem = new MenuItem(getString("menu_item.undo"));
        MenuItem redoItem = new MenuItem(getString("menu_item.redo"));
        undoItem.setOnAction(e -> UndoManager.undo());
        redoItem.setOnAction(e -> UndoManager.redo());
        editMenu.getItems().addAll(undoItem, redoItem);

        Menu extensionsMenu = new Menu(getString("menu.extensions"));
        MenuItem installItem = new MenuItem(getString("menu_item.install_extension"));
        MenuItem viewItem = new MenuItem(getString("menu_item.view_extensions"));
        MenuItem helpItem = new MenuItem(getString("menu_item.extension_help"));
        installItem.setOnAction(e -> ExtensionManager.promptInstall());
        viewItem.setOnAction(e -> ExtensionManager.openViewer());
        helpItem.setOnAction(e -> openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Extensions")));
        extensionsMenu.getItems().addAll(installItem, viewItem, helpItem);

        Menu supportMenu = new Menu(getString("menu.support"));
        MenuItem githubItem = new MenuItem("Github");
        MenuItem spigotItem = new MenuItem("Spigot");
        MenuItem discordItem = new MenuItem("Discord");
        spigotItem.setOnAction(e -> openURI(URI.create("https://www.spigotmc.org/resources/visual-bukkit-create-plugins.76474/")));
        githubItem.setOnAction(e -> openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit")));
        discordItem.setOnAction(e -> openURI(URI.create("https://discord.gg/ugkvGpu")));
        supportMenu.getItems().addAll(githubItem, spigotItem, discordItem);

        return new MenuBar(fileMenu, editMenu, extensionsMenu, SettingsManager.getInstance(), supportMenu);
    }

    private HBox loadButtonBar() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label projectLabel = new Label(String.format(getString("label.current_project"), "n/a"));
        ProjectManager.currentProjectProperty().addListener((o, oldValue, newValue) ->
                projectLabel.setText(String.format(getString("label.current_project"), newValue != null ? newValue.getDir().getFileName().toString() : "n/a")));
        HBox buttonBar = new HBox(
                new IconButton("add", getString("tooltip.add_component"), e -> ProjectManager.getCurrentProject().promptAddPluginComponent()),
                new IconButton("settings", getString("tooltip.plugin_settings"), e -> ProjectManager.getCurrentProject().getPluginSettingsStage().show()),
                new IconButton("folder", getString("tooltip.resource_files"), e -> openDirectory(ProjectManager.getCurrentProject().getResourceDir())),
                new IconButton("build", getString("tooltip.build_plugin"), e -> {
                    logDisplay.show();
                    PluginBuilder.build(ProjectManager.getCurrentProject());
                }),
                new IconButton("jar", getString("tooltip.build_directory"), e -> {
                    Path dir = ProjectManager.getCurrentProject().getBuildDir().resolve("target");
                    if (Files.exists(dir)) {
                        openDirectory(dir);
                    } else {
                        NotificationManager.displayError(getString("error.cannot_open_build_dir.title"), getString("error.cannot_open_build_dir.content"));
                    }
                }),
                new IconButton("log", getString("tooltip.log"), e -> logDisplay.show()), spacer, projectLabel);
        buttonBar.getStyleClass().add("button-bar");
        return buttonBar;
    }

    public void openURI(URI uri) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            NotificationManager.displayException("Failed to open URI", e);
        }
    }

    public void openDirectory(Path dir) {
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                NotificationManager.displayException("Failed to open directory", e);
                return;
            }
        }
        if (!Desktop.isDesktopSupported() || SystemUtils.IS_OS_UNIX || SystemUtils.IS_OS_LINUX) {
            try {
                Runtime.getRuntime().exec(new String[]{"xdg-open", dir.toUri().toString()});
            } catch (IOException ignored) {
                NotificationManager.displayError("Error", "Action not supported by your OS");
            }
        } else {
            openURI(dir.toUri());
        }
    }

    public void saveCurrentProject() throws IOException {
        Project currentProject = ProjectManager.getCurrentProject();
        if (currentProject != null) {
            currentProject.save();
        }
    }

    public boolean checkForUpdate() {
        try (InputStream inputStream = new URL("https://raw.githubusercontent.com/OfficialDonut/VisualBukkit/master/VB-Application/version").openStream()) {
            String latestVersion = IOUtils.toString(inputStream, StandardCharsets.UTF_8).trim();
            if (!VERSION.equals(latestVersion)) {
                ButtonType viewButton = new ButtonType(getString("button.view_update"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format(getString("dialog.update.content"), VERSION, latestVersion), viewButton, new ButtonType(getString("button.ignore_update")));
                alert.setTitle(getString("dialog.update.title"));
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

    public void setPluginComponentTabPane(TabPane tabPane) {
        innerBorderPane.setCenter(tabPane);
    }

    public static VisualBukkitApp getInstance() {
        return instance;
    }

    public static String getString(String key) {
        return resourceBundle.containsKey(key) ? resourceBundle.getString(key) : key;
    }

    public Path getDataDir() {
        return dataDir;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public BorderPane getRootPane() {
        return rootPane;
    }
}
