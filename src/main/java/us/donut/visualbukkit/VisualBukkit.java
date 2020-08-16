package us.donut.visualbukkit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.controlsfx.control.Notifications;
import us.donut.visualbukkit.blocks.BlockRegistry;
import us.donut.visualbukkit.editor.UndoManager;
import us.donut.visualbukkit.editor.ProjectManager;
import us.donut.visualbukkit.editor.SelectorPane;
import us.donut.visualbukkit.util.DataFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VisualBukkit extends Application {

    public static final String VERSION = "v" + VisualBukkitLauncher.class.getPackage().getSpecificationVersion();
    public static final Path DATA_FOLDER = Paths.get(System.getProperty("user.home"), "Visual Bukkit");
    public static final DataFile DATA_FILE = new DataFile(DATA_FOLDER.resolve("data.yml"));
    public static final Logger LOGGER = Logger.getLogger("VisualBukkit");
    private static VisualBukkit instance;

    private BorderPane rootPane = new BorderPane();
    private SplitPane splitPane = new SplitPane();
    private Scene scene = new Scene(rootPane, 500, 500);
    private Stage primaryStage;
    private Timeline autoSaveTimer;

    public VisualBukkit() {
        if (instance != null) {
            throw new IllegalStateException();
        }
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        if (Files.notExists(DATA_FOLDER)) {
            Files.createDirectory(DATA_FOLDER);
        }
        FileHandler fileHandler = new FileHandler(DATA_FOLDER.resolve("log.txt").toString(), true);
        fileHandler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        Thread.setDefaultUncaughtExceptionHandler((thread, e) -> Platform.runLater(() -> displayException("An exception occurred", e)));
        Platform.runLater(this::load);
    }

    private void load() {
        primaryStage.setTitle("Visual Bukkit " + VERSION);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        rootPane.getStylesheets().add("/style.css");

        try (InputStream inputStream = VisualBukkit.class.getResourceAsStream("/icon.png")) {
            primaryStage.getIcons().add(new Image(inputStream));
        } catch (IOException e) {
            displayException("Failed to load icon", e);
        }

        BlockRegistry.registerAll();
        createMenuBar();
        setupSaving();
        splitPane.getItems().addAll(new SelectorPane(), new Pane(), new Pane());
        rootPane.setCenter(splitPane);
        notifyPreloader(new Preloader.ProgressNotification(1));
        primaryStage.show();
        ProjectManager.init();
    }

    private void createMenuBar() {
        MenuItem saveItem = new MenuItem("Save");
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem importItem = new MenuItem("Import");
        MenuItem exportItem = new MenuItem("Export");
        MenuItem exitItem = new MenuItem("Exit");
        saveItem.setOnAction(e -> save(true));
        newItem.setOnAction(e -> ProjectManager.promptCreateProject(true));
        openItem.setOnAction(e -> ProjectManager.promptOpenProject());
        deleteItem.setOnAction(e -> ProjectManager.promptDeleteProject());
        importItem.setOnAction(e -> ProjectManager.promptImportProject());
        exportItem.setOnAction(e -> ProjectManager.promptExportProject());
        exitItem.setOnAction(e -> primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().addAll(newItem, openItem, deleteItem, new SeparatorMenuItem(), importItem, exportItem, new SeparatorMenuItem(), saveItem, exitItem);

        MenuItem undoItem = new MenuItem("Undo");
        MenuItem redoItem = new MenuItem("Redo");
        undoItem.setOnAction(e -> UndoManager.undo());
        redoItem.setOnAction(e -> UndoManager.redo());
        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(undoItem, redoItem);

        Menu fontSizeMenu = new Menu("Font Size");
        ToggleGroup fontToggleGroup = new ToggleGroup();
        int fontSize = DATA_FILE.getInt("font-size", 14);
        for (int i = 8; i <= 36; i++) {
            int size = i;
            RadioMenuItem sizeItem = new RadioMenuItem(String.valueOf(size));
            sizeItem.setOnAction(e -> {
                rootPane.setStyle("-fx-font-size:" + size + ";");
                DATA_FILE.set("font-size", size);
            });
            fontToggleGroup.getToggles().add(sizeItem);
            fontSizeMenu.getItems().add(sizeItem);
            if (i == fontSize) {
                sizeItem.setSelected(true);
                rootPane.setStyle("-fx-font-size:" + fontSize + ";");
            }
        }
        Menu autosaveMenu = new Menu("Autosave");
        ToggleGroup autosaveToggleGroup = new ToggleGroup();
        int autoSaveDuration = DATA_FILE.getInt("autosave", -1);
        for (int duration : new int[]{5, 15, 30, -1}) {
            RadioMenuItem durationItem;
            if (duration != -1) {
                durationItem = new RadioMenuItem(duration + " minutes");
                durationItem.setOnAction(e -> {
                    if (autoSaveTimer != null) {
                        autoSaveTimer.stop();
                    }
                    autoSave(duration);
                    DATA_FILE.set("autosave", duration);
                });
            } else {
                durationItem = new RadioMenuItem("Never");
                durationItem.setOnAction(e -> {
                    if (autoSaveTimer != null) {
                        autoSaveTimer.stop();
                    }
                    DATA_FILE.set("autosave", null);
                });
            }
            autosaveToggleGroup.getToggles().add(durationItem);
            autosaveMenu.getItems().add(durationItem);
            if (duration == autoSaveDuration) {
                durationItem.setSelected(true);
                if (autoSaveDuration != -1) {
                    autoSave(duration);
                }
            }
        }
        Menu settingsMenu = new Menu("Settings");
        settingsMenu.getItems().addAll(fontSizeMenu, autosaveMenu);

        MenuItem githubItem = new MenuItem("Github");
        githubItem.setOnAction(e -> openURI("https://github.com/OfficialDonut/VisualBukkit"));
        MenuItem spigotItem = new MenuItem("Spigot");
        spigotItem.setOnAction(e -> openURI("https://www.spigotmc.org/resources/visual-bukkit-create-plugins.76474/"));
        MenuItem discordItem = new MenuItem("Discord");
        discordItem.setOnAction(e -> openURI("https://discord.gg/ugkvGpu"));
        Menu supportMenu = new Menu("Support");
        supportMenu.getItems().addAll(githubItem, spigotItem, discordItem);

        rootPane.setTop(new MenuBar(fileMenu, editMenu, settingsMenu, supportMenu));
    }

    private void setupSaving() {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.isShortcutDown() && e.getCode() == KeyCode.S) {
                save(true);
                e.consume();
            }
        });

        Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to save?");
        saveAlert.setHeaderText(null);
        saveAlert.setGraphic(null);
        ButtonType saveButton = new ButtonType("Save and exit");
        ButtonType noSaveButton = new ButtonType("Exit without saving");
        saveAlert.getButtonTypes().setAll(saveButton, noSaveButton, ButtonType.CANCEL);

        primaryStage.setOnCloseRequest(e -> {
            if (ProjectManager.getCurrentProject() != null) {
                saveAlert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType.equals(saveButton)) {
                        save(false);
                        Platform.exit();
                    } else if (buttonType.equals(noSaveButton)) {
                        Platform.exit();
                    }
                });
            } else {
                Platform.exit();
            }
            e.consume();
        });
    }

    private void openURI(String uri) {
        try {
            Desktop.getDesktop().browse(URI.create(uri));
        } catch (IOException e) {
            displayException("Failed to open " + uri, e);
        }
    }

    private void autoSave(double minutes) {
        autoSaveTimer = new Timeline(new KeyFrame(Duration.minutes(minutes), e -> save(false)));
        autoSaveTimer.setCycleCount(Timeline.INDEFINITE);
        autoSaveTimer.play();
    }

    private void save(boolean notification) {
        try {
            if (ProjectManager.getCurrentProject() != null) {
                ProjectManager.getCurrentProject().save();
            }
            DATA_FILE.save();
            if (notification) {
                displayMessage("Saved", "Successfully saved");
            }
        } catch (IOException e) {
            displayException("Failed to save", e);
        }
    }

    public static void displayMessage(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showInformation();
    }

    public static void displayError(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showError();
        Toolkit.getDefaultToolkit().beep();
    }

    public static void displayException(String message, Throwable e) {
        LOGGER.severe(ExceptionUtils.getStackTrace(e));
        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        VBox content = new VBox(5, new Text(message), new TextArea(ExceptionUtils.getStackTrace(e)));
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
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

    public BorderPane getRootPane() {
        return rootPane;
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }
}
