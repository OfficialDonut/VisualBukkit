package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.BackgroundTaskExecutor;
import com.gmail.visualbukkit.ui.PopupWindow;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.property.BeanPropertyUtils;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Project {

    private static final DefaultArtifact PAPER_ARTIFACT = new DefaultArtifact("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT", Collections.singletonMap("scope", "provided"));
    private static final RemoteRepository PAPER_REPO = new RemoteRepository.Builder("papermc", "default", "https://repo.papermc.io/repository/maven-public/").build();

    private final Path directory;
    private final Path dataFile;
    private final Path pluginComponentDirectory;
    private final Path resourcesDirectory;
    private final Path buildDirectory;

    private final BorderPane projectPane = new BorderPane();
    private final SplitPane splitPane = new SplitPane();
    private final VBox placeholderPane = new VBox();
    private final TabPane tabPane = new TabPane();
    private final StatementSelector statementSelector = new StatementSelector();
    private final PluginSettings pluginSettings = new PluginSettings();
    private final Map<String, PluginComponentBlock> pluginComponents = new HashMap<>();

    public Project(Path directory) {
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        pluginComponentDirectory = directory.resolve("plugin_components");
        resourcesDirectory = directory.resolve("resource_files");
        buildDirectory = directory.resolve("build");

        PropertySheet pluginSettingsSheet = new PropertySheet();
        pluginSettingsSheet.setModeSwitcherVisible(false);
        pluginSettingsSheet.setSearchBoxVisible(false);
        pluginSettingsSheet.getItems().setAll(BeanPropertyUtils.getProperties(pluginSettings));
        PopupWindow pluginSettingsWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_settings"), pluginSettingsSheet);

        Button addComponentButton = new Button(VisualBukkitApp.localizedText("button.add_plugin_component"));
        Button pluginComponentsButton = new Button(VisualBukkitApp.localizedText("button.plugin_components"));
        Button pluginSettingsButton = new Button(VisualBukkitApp.localizedText("button.plugin_settings"));
        Button buildPluginButton = new Button(VisualBukkitApp.localizedText("button.build_plugin"));
        Button modulesButton = new Button(VisualBukkitApp.localizedText("button.extension_modules"));
        addComponentButton.setOnAction(e -> promptAddPluginComponent());
        pluginComponentsButton.setOnAction(e -> showPluginComponents());
        pluginSettingsButton.setOnAction(e -> pluginSettingsWindow.show());
        modulesButton.setOnAction(e -> {});
        buildPluginButton.setOnAction(e -> PluginBuilder.build(this));
        HBox buttonBar = new HBox(addComponentButton, pluginComponentsButton, pluginSettingsButton, buildPluginButton, modulesButton);
        buttonBar.getStyleClass().add("button-bar");

        placeholderPane.getChildren().add(new Label(VisualBukkitApp.localizedText("label.add_plugin_component")));
        placeholderPane.setAlignment(Pos.CENTER);
        placeholderPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                promptAddPluginComponent();
            }
        });

        splitPane.getItems().addAll(statementSelector, placeholderPane);
        splitPane.widthProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> splitPane.setDividerPositions(0.15)));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        projectPane.setCenter(splitPane);
        projectPane.getStyleClass().add("project-pane");
        projectPane.setBottom(buttonBar);
    }

    public void updateBlockStates() {
        pluginComponents.values().forEach(PluginComponentBlock::updateState);
    }

    public void open() {
        BlockRegistry.clear();
        ClassRegistry.clear();
        UndoManager.clear();

        BackgroundTaskExecutor.executeAndWait(() -> {
            try {
                BlockRegistry.register(BlockRegistry.class.getClassLoader(), "com.gmail.visualbukkit.blocks.definitions");
                ClassRegistry.register(PAPER_ARTIFACT, PAPER_REPO, "org.bukkit", "com.destroystokyo.paper");
                Pattern classPattern = Pattern.compile("/modules/java\\.base/(.+)\\.class");
                FileSystem fileSystem = FileSystems.getFileSystem(URI.create("jrt:/"));
                Files.walkFileTree(fileSystem.getPath("/modules/java.base/java"), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                        Matcher matcher = classPattern.matcher(file.toString());
                        if (matcher.matches()) {
                            try {
                                Class<?> clazz = Class.forName(matcher.group(1).replace("/", "."));
                                if (!clazz.isAnonymousClass()) {
                                    ClassRegistry.register(clazz);
                                }
                            } catch (ClassNotFoundException e) {
                                VisualBukkitApp.getLogger().log(Level.WARNING, "Failed to register class", e);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException | MavenInvocationException | DependencyResolutionException e) {
                Platform.runLater(() -> VisualBukkitApp.displayException(e));
            }
        });

        if (Files.exists(dataFile)) {
            try {
                JSONObject json = new JSONObject(Files.readString(dataFile));
                JSONObject pluginSettingsJson = json.optJSONObject("plugin-settings");
                if (pluginSettingsJson != null) {
                    pluginSettings.deserialize(pluginSettingsJson);
                }
                JSONArray openPluginComponents = json.optJSONArray("open-plugin-components");
                if (openPluginComponents != null) {
                    for (Object object : openPluginComponents) {
                        if (object instanceof String name) {
                            openPluginComponent(name);
                        }
                    }
                    tabPane.getSelectionModel().select(json.optInt("selected-tab"));
                }
            } catch (IOException | JSONException e) {
                VisualBukkitApp.displayException(e);
            }
        }

        statementSelector.refreshStatements();
        VisualBukkitApp.getRootPane().setCenter(projectPane);
        VisualBukkitApp.getData().put("current-project", getName());
    }

    public void save() {
        try {
            JSONObject json = new JSONObject();
            json.put("plugin-settings", pluginSettings.serialize());
            json.put("selected-tab", tabPane.getSelectionModel().getSelectedIndex());
            for (Tab tab : tabPane.getTabs()) {
                json.append("open-plugin-components", tab.getText());
            }
            Files.createDirectories(directory);
            Files.writeString(dataFile, json.toString(2));
            for (Map.Entry<String, PluginComponentBlock> entry : pluginComponents.entrySet()) {
                savePluginComponent(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            VisualBukkitApp.displayException(e);
        }
    }

    private void savePluginComponent(String name, PluginComponentBlock block) throws IOException {
        Files.createDirectories(pluginComponentDirectory);
        Files.writeString(pluginComponentDirectory.resolve(name), block.serialize().toString());
    }

    public void showPluginComponents() {
        ListView<String> listView = new ListView<>();
        listView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_plugin_components")));
        if (Files.exists(pluginComponentDirectory)) {
            String[] files = pluginComponentDirectory.toFile().list();
            if (files != null) {
                listView.getItems().setAll(new TreeSet<>(Arrays.asList(files)));
            }
        }
        Button addButton = new Button(VisualBukkitApp.localizedText("button.add"));
        Button openAllButton = new Button(VisualBukkitApp.localizedText("button.open_all"));
        Button openButton = new Button(VisualBukkitApp.localizedText("button.open"));
        Button deleteButton = new Button(VisualBukkitApp.localizedText("button.delete"));
        Button closeButton = new Button(VisualBukkitApp.localizedText("button.close"));
        HBox buttonBar = new HBox(addButton, openAllButton, openButton, deleteButton, closeButton);
        buttonBar.getStyleClass().add("button-bar");
        VBox vBox = new VBox(listView, buttonBar);
        vBox.getStyleClass().add("plugin-component-list");
        PopupWindow popupWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_components"), vBox);
        addButton.setOnAction(e -> {
            popupWindow.close();
            promptAddPluginComponent();
        });
        openAllButton.setOnAction(e -> {
            popupWindow.close();
            for (String item : listView.getItems()) {
                openPluginComponent(item);
            }
        });
        openButton.setOnAction(e -> {
            popupWindow.close();
            openPluginComponent(listView.getSelectionModel().getSelectedItem());
        });
        deleteButton.setOnAction(e -> {
            popupWindow.close();
            promptDeletePluginComponent(listView.getSelectionModel().getSelectedItem());
        });
        closeButton.setOnAction(e -> popupWindow.close());
        openButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(openButton.disableProperty());
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item != null ? item : "");
                    }
                };
                cell.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                        popupWindow.close();
                        openPluginComponent(listView.getSelectionModel().getSelectedItem());
                    }
                });
                return cell;
            }
        });
        popupWindow.show();
    }

    private PluginComponentBlock loadPluginComponent(String name) {
        try {
            JSONObject pluginComponentJson = new JSONObject(Files.readString(pluginComponentDirectory.resolve(name)));
            return BlockRegistry.getPluginComponent(pluginComponentJson.optString("uid")).newBlock(pluginComponentJson);
        } catch (IOException | JSONException e) {
            VisualBukkitApp.displayException(e);
            return new PluginComponentBlock.Unknown();
        }
    }

    private void openPluginComponent(String name) {
        if (!pluginComponents.containsKey(name)) {
            addPluginComponent(name, loadPluginComponent(name));
        }
        tabPane.getSelectionModel().select(getTab(name));
    }

    private void addPluginComponent(String name, PluginComponentBlock block) {
        Tab tab = new Tab(name, new PluginComponentPane(block));
        pluginComponents.put(name, block);
        tabPane.getTabs().add(tab);
        splitPane.getItems().set(1, tabPane);
        block.updateState();
        tab.setOnClosed(e -> {
            try {
                savePluginComponent(name, block);
                pluginComponents.remove(name);
                UndoManager.clear();
                if (tabPane.getTabs().isEmpty()) {
                    splitPane.getItems().set(1, placeholderPane);
                }
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.plugin_component_saved"));
            } catch (IOException ex) {
                VisualBukkitApp.displayException(ex);
            }
        });
    }

    public Set<PluginComponentBlock> getPluginComponents() {
        Set<PluginComponentBlock> blocks = new HashSet<>(pluginComponents.values());
        if (Files.exists(pluginComponentDirectory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(pluginComponentDirectory)) {
                for (Path path : stream) {
                    String name = path.getFileName().toString();
                    if (!pluginComponents.containsKey(name)) {
                        blocks.add(loadPluginComponent(name));
                    }
                }
            } catch (IOException e) {
                VisualBukkitApp.displayException(e);
            }
        }
        return blocks;
    }

    public void promptAddPluginComponent() {
        TextField nameField = new TextField();
        SearchableComboBox<PluginComponentBlock.Factory> typeComboBox = new SearchableComboBox<>();
        typeComboBox.getItems().addAll(BlockRegistry.getPluginComponents());
        typeComboBox.getSelectionModel().selectFirst();
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("dialog.add_component_name")), nameField);
        gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("dialog.add_component_type")), typeComboBox);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK && isPluginComponentNameValid(nameField.getText())) {
                try {
                    PluginComponentBlock block = typeComboBox.getValue().newBlock();
                    savePluginComponent(nameField.getText(), block);
                    addPluginComponent(nameField.getText(), block);
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.plugin_component_added"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    private void promptDeletePluginComponent(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_delete_plugin_component"), name));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    Files.deleteIfExists(pluginComponentDirectory.resolve(name));
                    pluginComponents.remove(name);
                    tabPane.getTabs().remove(getTab(name));
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.plugin_component_deleted"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    private boolean isPluginComponentNameValid(String name) {
        if (name.isBlank() || !StringUtils.isAlphanumeric(name)) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.plugin_component_invalid_name"));
            return false;
        }
        if (Files.exists(pluginComponentDirectory.resolve(name))) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.plugin_component_duplicate"));
            return false;
        }
        return true;
    }

    private Tab getTab(String name) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equals(name)) {
                return tab;
            }
        }
        return null;
    }

    public String getName() {
        return directory.getFileName().toString();
    }

    public Path getDirectory() {
        return directory;
    }

    public Path getPluginComponentDirectory() {
        return pluginComponentDirectory;
    }

    public Path getResourcesDirectory() {
        return resourcesDirectory;
    }

    public Path getBuildDirectory() {
        return buildDirectory;
    }

    public PluginSettings getPluginSettings() {
        return pluginSettings;
    }
}
