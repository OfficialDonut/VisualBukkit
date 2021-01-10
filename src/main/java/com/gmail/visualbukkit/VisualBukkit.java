package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.extensions.ExtensionManager;
import com.gmail.visualbukkit.gui.*;
import com.gmail.visualbukkit.plugin.PluginBuilder;
import com.gmail.visualbukkit.plugin.ProjectManager;
import com.gmail.visualbukkit.util.AutoSaver;
import com.gmail.visualbukkit.util.DataFile;
import com.google.common.io.CharStreams;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.eclipse.fx.ui.controls.tabpane.DndTabPane;
import org.eclipse.fx.ui.controls.tabpane.DndTabPaneFactory;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VisualBukkit extends Application {

    private static String version = "v" + VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    private static Path dataFolder = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
    private static DataFile dataFile = new DataFile(dataFolder.resolve("data.json"));
    private static Logger logger = Logger.getLogger("VisualBukkit");
    private static FileHandler logFileHandler;
    private static VisualBukkit instance;
    private static boolean saveOnExit = true;

    private BorderPane rootPane = new BorderPane();
    private Scene scene = new Scene(rootPane, 500, 500);
    private Stage primaryStage;
    private BlockSelector blockSelector;
    private ElementInspector elementInspector;
    private ProjectView projectView;
    private DndTabPane canvasPane;

    public VisualBukkit() {
        if (instance != null) {
            throw new IllegalStateException();
        }
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        if (Files.notExists(dataFolder)) {
            Files.createDirectory(dataFolder);
        }
        logFileHandler = new FileHandler(dataFolder.resolve("log.txt").toString(), true);
        logFileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(logFileHandler);
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> Platform.runLater(() -> NotificationManager.displayException("An exception occurred", e)));
        Platform.runLater(this::load);
    }

    @Override
    public void stop() {
        if (saveOnExit) {
            save(false);
        }
        logFileHandler.close();
    }

    private void load() {
        primaryStage.setTitle("Visual Bukkit " + version);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        rootPane.getStylesheets().add("/style.css");

        try (InputStream inputStream = VisualBukkit.class.getResourceAsStream("/icon.png")) {
            primaryStage.getIcons().add(new Image(inputStream));
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load icon", e);
        }

        blockSelector = new BlockSelector();
        elementInspector = new ElementInspector();
        projectView = new ProjectView();
        canvasPane = (DndTabPane) DndTabPaneFactory.createDefaultDnDPane(DndTabPaneFactory.FeedbackType.MARKER, null).getChildren().get(0);
        canvasPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        SplitPane splitPane = new SplitPane();
        SplitPane sideSplitPane = new SplitPane();
        sideSplitPane.setOrientation(Orientation.VERTICAL);
        sideSplitPane.getItems().addAll(elementInspector, projectView);
        splitPane.getItems().addAll(blockSelector, canvasPane, sideSplitPane);

        rootPane.setCenter(splitPane);
        rootPane.setTop(createMenuBar());

        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.S) {
                save(true);
                e.consume();
            }
        });

        ExtensionManager.init();
        BlockRegistry.registerBlocks("com.gmail.visualbukkit.blocks");
        blockSelector.loadFavorites();
        blockSelector.getSelectionModel().select(0);

        notifyPreloader(new Preloader.ProgressNotification(1));
        primaryStage.show();

        splitPane.setDividerPositions(0.2, 0.8);
        sideSplitPane.setDividerPositions(0.5);
        ProjectManager.openLast();

        checkForUpdate();
    }

    private MenuBar createMenuBar() {
        MenuItem saveItem = new MenuItem("Save Project");
        MenuItem renameItem = new MenuItem("Rename Project");
        MenuItem openItem = new MenuItem("Open Project");
        MenuItem newItem = new MenuItem("New Project");
        MenuItem deleteItem = new MenuItem("Delete Project");
        MenuItem importItem = new MenuItem("Import Project");
        MenuItem exportItem = new MenuItem("Export Project");
        MenuItem exitItem = new MenuItem("Save and Exit");
        MenuItem exitNoSaveItem = new MenuItem("Exit w/o Saving");
        MenuItem updateItem = new MenuItem("Check for Update");
        saveItem.setOnAction(e -> save(true));
        renameItem.setOnAction(e -> ProjectManager.getCurrentProject().promptRename());
        openItem.setOnAction(e -> ProjectManager.promptOpenProject());
        newItem.setOnAction(e -> ProjectManager.promptCreateProject(true));
        deleteItem.setOnAction(e -> ProjectManager.promptDeleteProject());
        importItem.setOnAction(e -> ProjectManager.promptImportProject());
        exportItem.setOnAction(e -> ProjectManager.promptExportProject());
        exitItem.setOnAction(e -> primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        exitNoSaveItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit without saving?");
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
                NotificationManager.displayMessage("No update", "Running latest Visual Bukkit version");
            }
        });
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                saveItem, renameItem, new SeparatorMenuItem(),
                openItem, newItem, deleteItem, importItem, exportItem, new SeparatorMenuItem(),
                exitItem, exitNoSaveItem, new SeparatorMenuItem(),
                updateItem);

        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        undoItem.setOnAction(e -> UndoManager.undo());
        redoItem.setOnAction(e -> UndoManager.redo());
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(undoItem, redoItem);

        MenuItem manageItem = new MenuItem("Manage");
        MenuItem helpItem = new MenuItem("Help");
        manageItem.setOnAction(e -> ExtensionManager.promptManage());
        helpItem.setOnAction(e -> openURI("https://github.com/OfficialDonut/VisualBukkit/wiki/Extensions"));
        Menu extensionsMenu = new Menu("Extensions");
        extensionsMenu.getItems().addAll(manageItem, helpItem);

        MenuItem resourceFilesItem = new MenuItem("Resource Files");
        MenuItem addCanvasItem = new MenuItem("Add Canvas");
        MenuItem buildItem = new MenuItem("Build Plugin");
        resourceFilesItem.setOnAction(e -> ProjectManager.getCurrentProject().openResourceFolder());
        addCanvasItem.setOnAction(e -> ProjectManager.getCurrentProject().promptAddCanvas());
        buildItem.setOnAction(e -> PluginBuilder.build(ProjectManager.getCurrentProject()));
        Menu pluginMenu = new Menu("Plugin");
        pluginMenu.getItems().addAll(addCanvasItem, resourceFilesItem, buildItem);

        Menu fontSizeMenu = new Menu("Font Size");
        ToggleGroup fontToggleGroup = new ToggleGroup();
        int fontSize = dataFile.getJson().optInt("font-size", 14);
        for (int i : new int[]{8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36}) {
            RadioMenuItem sizeItem = new RadioMenuItem(String.valueOf(i));
            sizeItem.setOnAction(e -> {
                rootPane.setStyle("-fx-font-size:" + i + ";");
                dataFile.getJson().put("font-size", i);
            });
            fontToggleGroup.getToggles().add(sizeItem);
            fontSizeMenu.getItems().add(sizeItem);
            if (i == fontSize) {
                sizeItem.setSelected(true);
                rootPane.setStyle("-fx-font-size:" + fontSize + ";");
            }
        }
        Menu autoSaveMenu = new Menu("Auto-save");
        ToggleGroup autoSaveToggleGroup = new ToggleGroup();
        int autoSaveDuration = dataFile.getJson().optInt("auto-save", -1);
        AutoSaver autoSaver = new AutoSaver();
        for (int duration : new int[]{5, 10, 15, 20, 25, 30, -1}) {
            RadioMenuItem durationItem = new RadioMenuItem(duration == -1 ? "Never" : duration + " minutes");
            durationItem.setOnAction(e -> {
                autoSaver.setTime(duration);
                dataFile.getJson().put("auto-save", duration);
            });
            autoSaveToggleGroup.getToggles().add(durationItem);
            autoSaveMenu.getItems().add(durationItem);
            if (duration == autoSaveDuration) {
                durationItem.setSelected(true);
                autoSaver.setTime(duration);
            }
        }
        Menu settingsMenu = new Menu("Settings");
        settingsMenu.getItems().addAll(fontSizeMenu, autoSaveMenu);

        MenuItem githubItem = new MenuItem("Github");
        githubItem.setOnAction(e -> openURI("https://github.com/OfficialDonut/VisualBukkit"));
        MenuItem spigotItem = new MenuItem("Spigot");
        spigotItem.setOnAction(e -> openURI("https://www.spigotmc.org/resources/visual-bukkit-create-plugins.76474/"));
        MenuItem discordItem = new MenuItem("Discord");
        discordItem.setOnAction(e -> openURI("https://discord.gg/ugkvGpu"));
        Menu supportMenu = new Menu("Support");
        supportMenu.getItems().addAll(githubItem, spigotItem, discordItem);

        return new MenuBar(fileMenu, editMenu, pluginMenu, extensionsMenu, settingsMenu, supportMenu);
    }

    public void save(boolean notification) {
        try {
            if (ProjectManager.getCurrentProject() != null) {
                ProjectManager.getCurrentProject().save();
            }
            dataFile.save();
            if (notification) {
                NotificationManager.displayMessage("Saved", "Successfully saved");
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to save", e);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private boolean checkForUpdate() {
        if(true){
            return false;
        }
        try (InputStreamReader reader = new InputStreamReader(new URL("https://raw.githubusercontent.com/OfficialDonut/VisualBukkit/master/version").openStream(), StandardCharsets.UTF_8)) {
            if (!version.equals("v" + CharStreams.toString(reader).trim())) {
                ButtonType viewButton = new ButtonType("View Update");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "An update is available", viewButton, new ButtonType("Ignore"));
                alert.setTitle("Update Available");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == viewButton) {
                        openURI("https://github.com/OfficialDonut/VisualBukkit/releases");
                    }
                });
                return true;
            }
        } catch (IOException ignored) {}
        return false;
    }

    private void openURI(String uri) {
        try {
            Desktop.getDesktop().browse(URI.create(uri));
        } catch (IOException e) {
            NotificationManager.displayException("Failed to open " + uri, e);
        }
    }

    public static String getVersion() {
        return version;
    }

    public static Path getDataFolder() {
        return dataFolder;
    }

    public static DataFile getDataFile() {
        return dataFile;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static VisualBukkit getInstance() {
        return instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    public BlockSelector getBlockSelector() {
        return blockSelector;
    }

    public ElementInspector getElementInspector() {
        return elementInspector;
    }

    public DndTabPane getCanvasPane() {
        return canvasPane;
    }

    public ProjectView getProjectView() {
        return projectView;
    }
}
