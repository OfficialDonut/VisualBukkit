package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.VisualBukkitExtension;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.definitions.CompCommand;
import com.gmail.visualbukkit.project.maven.MavenDependencyModule;
import com.gmail.visualbukkit.project.maven.MavenModule;
import com.gmail.visualbukkit.project.maven.MavenRepositoryModule;
import com.gmail.visualbukkit.project.maven.MavenUtil;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.ActionButton;
import com.gmail.visualbukkit.ui.BackgroundTaskExecutor;
import com.gmail.visualbukkit.ui.PopupWindow;
import com.gmail.visualbukkit.ui.IconButton;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.io.Resources;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.controlsfx.control.CheckTreeView;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.action.Action;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Stream;

public class Project {

    private final Path directory;
    private final Path dataFile;
    private final Path pluginComponentDirectory;
    private final Path resourcesDirectory;
    private final Path buildDirectory;
    private JSONObject data = new JSONObject();
    private boolean reloadRequired;

    private final BorderPane projectPane = new BorderPane();
    private final SplitPane splitPane = new SplitPane();
    private final VBox placeholderPane = new VBox();
    private final TabPane tabPane = new TabPane();
    private final StatementSelector statementSelector = new StatementSelector();
    private final PluginSettings pluginSettings = new PluginSettings();
    private final ListView<MavenModule> mavenListView = new ListView<>();
    private final ListSelectionView<PluginModule> moduleSelector = new ListSelectionView<>();
    private final Set<PluginComponent> pluginComponents = new TreeSet<>();
    private final Map<PluginComponent, Tab> openPluginComponents = new HashMap<>();
    private final TextField jarOutputField = new TextField();
    private final CheckBox debugModeCheckBox = new CheckBox(VisualBukkitApp.localizedText("label.enabled"));

    public Project(Path directory) {
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        pluginComponentDirectory = directory.resolve("plugin_components");
        resourcesDirectory = directory.resolve("resource_files");
        buildDirectory = directory.resolve("build");

        moduleSelector.getTargetItems().addListener((ListChangeListener<PluginModule>) c -> reloadRequired = true);
        moduleSelector.setSourceHeader(new Label(VisualBukkitApp.localizedText("label.disabled")));
        moduleSelector.setTargetHeader(new Label(VisualBukkitApp.localizedText("label.enabled")));
        for (Action action : moduleSelector.getActions()) {
            action.graphicProperty().unbind();
            action.setGraphic(null);
            action.setText(action instanceof ListSelectionView.MoveToTarget ? ">" : action instanceof ListSelectionView.MoveToTargetAll ? ">>" : action instanceof ListSelectionView.MoveToSource ? "<" : "<<");
        }

        ActionButton addDependButton = new ActionButton(VisualBukkitApp.localizedText("button.add_dependency"), e -> promptAddMavenDependency(null));
        ActionButton addRepoButton = new ActionButton(VisualBukkitApp.localizedText("button.add_repository"), e -> promptAddMavenRepository(null));
        ActionButton editButton = new ActionButton(VisualBukkitApp.localizedText("button.edit"), e -> {
            MavenModule selectedMavenModule = mavenListView.getSelectionModel().getSelectedItem();
            if (selectedMavenModule instanceof MavenDependencyModule mavenDependency) {
                promptAddMavenDependency(mavenDependency);
            } else if (selectedMavenModule instanceof MavenRepositoryModule mavenRepository) {
                promptAddMavenRepository(mavenRepository);
            }
        });
        ActionButton deleteButton = new ActionButton(VisualBukkitApp.localizedText("button.delete"), e -> {
            mavenListView.getItems().remove(mavenListView.getSelectionModel().getSelectedItem());
            reloadRequired = true;
        });
        editButton.disableProperty().bind(mavenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(editButton.disableProperty());
        VBox mavenPane = new VBox(new Label(VisualBukkitApp.localizedText("label.maven_settings")), mavenListView, new HBox(addDependButton, addRepoButton, editButton, deleteButton));
        mavenPane.getStyleClass().add("maven-settings-pane");

        Tab pluginYmlTab = new Tab(VisualBukkitApp.localizedText("label.plugin_attributes"), pluginSettings.getGrid());
        Tab mavenTab = new Tab(VisualBukkitApp.localizedText("label.maven"), mavenPane);
        Tab modulesTab = new Tab(VisualBukkitApp.localizedText("label.modules"), moduleSelector);
        TabPane settingsTabPane = new TabPane(pluginYmlTab, modulesTab, mavenTab, new Tab());
        settingsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        PopupWindow pluginSettingsWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_settings"), settingsTabPane);
        pluginSettingsWindow.setOnShowing(e -> {
            CheckBoxTreeItem<Object> rootItem = new CheckBoxTreeItem<>(VisualBukkitApp.localizedText("label.plugin_components"));
            rootItem.setSelected(true);
            rootItem.setExpanded(true);
            for (PluginComponent pluginComponent : pluginComponents) {
                CheckBoxTreeItem<Object> treeItem = new CheckBoxTreeItem<>(pluginComponent);
                treeItem.setSelected(!pluginComponent.isDisabled());
                pluginComponent.disabledProperty().bind(treeItem.selectedProperty().not());
                rootItem.getChildren().add(treeItem);
            }
            Label label = new Label(VisualBukkitApp.localizedText("label.included"));
            GridPane.setValignment(label, VPos.TOP);
            GridPane gridPane = new GridPane();
            gridPane.addRow(2, label, new CheckTreeView<>(rootItem));
            gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("label.jar_output")), new HBox(jarOutputField, new IconButton(FontAwesomeRegular.FOLDER_OPEN, e2 -> {
                File dir = new DirectoryChooser().showDialog(pluginSettingsWindow);
                if (dir != null) {
                    jarOutputField.setText(dir.getAbsolutePath());
                }
            })));
            gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("label.debug_mode")), debugModeCheckBox);
            gridPane.getStyleClass().add("build-settings-pane");
            settingsTabPane.getTabs().set(3, new Tab(VisualBukkitApp.localizedText("label.build"), gridPane));
        });
        pluginSettingsWindow.setOnHidden(e -> {
            if (reloadRequired) {
                reloadRequired = false;
                ProjectManager.open(getName());
            }
        });

        Button buildButton = new Button(VisualBukkitApp.localizedText("button.build_plugin"));
        buildButton.setGraphic(new FontIcon(FontAwesomeSolid.HAMMER));
        buildButton.setOnAction(e -> {
            buildButton.setDisable(true);
            build().whenComplete((u, t) -> Platform.runLater(() -> buildButton.setDisable(false)));
        });

        HBox buttonBar = new HBox(
                new ActionButton(VisualBukkitApp.localizedText("button.add_component"), FontAwesomeSolid.PLUS, e -> promptAddPluginComponent()),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_components"), FontAwesomeSolid.LIST, e -> showPluginComponents()),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_settings"), FontAwesomeSolid.COG, e -> pluginSettingsWindow.show()),
                buildButton);
        buttonBar.getStyleClass().add("button-bar");

        placeholderPane.getChildren().add(new Label(VisualBukkitApp.localizedText("label.add_component")));
        placeholderPane.setAlignment(Pos.CENTER);
        placeholderPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                promptAddPluginComponent();
            }
        });

        splitPane.getItems().addAll(statementSelector, placeholderPane);
        splitPane.widthProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> splitPane.setDividerPositions(0.175)));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        projectPane.setCenter(splitPane);
        projectPane.getStyleClass().add("project-pane");
        projectPane.setBottom(buttonBar);
    }

    protected void open() throws IOException {
        if (Files.exists(dataFile)) {
            try {
                data = new JSONObject(Files.readString(dataFile));
            } catch (IOException | JSONException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to load data file", e);
            }
        }

        debugModeCheckBox.setSelected(data.optBoolean("debug-mode"));
        jarOutputField.setText(data.optString("jar-output", buildDirectory.resolve("target").toString()));
        pluginSettings.deserialize(data.optJSONObject("plugin-settings", new JSONObject()));

        JSONArray enabledModulesJson = data.optJSONArray("enabled-modules");
        List<Object> enabledModules = enabledModulesJson != null ? enabledModulesJson.toList() : Collections.emptyList();
        for (PluginModule module : PluginModuleRegistry.getPluginModules()) {
            (enabledModules.contains(module.getID()) ? moduleSelector.getTargetItems() : moduleSelector.getSourceItems()).add(module);
        }
        JSONArray mavenRepoJson = data.optJSONArray("maven-repositories");
        if (mavenRepoJson != null) {
            for (Object o : mavenRepoJson) {
                mavenListView.getItems().add(MavenRepositoryModule.deserialize((JSONObject) o));
            }
        }
        JSONArray mavenDependJson = data.optJSONArray("maven-dependencies");
        if (mavenDependJson != null) {
            for (Object o : mavenDependJson) {
                mavenListView.getItems().add(MavenDependencyModule.deserialize((JSONObject) o));
            }
        }

        BackgroundTaskExecutor.executeAndWait(() -> {
            try {
                BlockRegistry.register(Project.class.getClassLoader(), "com.gmail.visualbukkit.blocks.definitions");
                ClassRegistry.register(Project.class.getClassLoader(), "classes/jdk");
                ClassRegistry.register(Project.class.getClassLoader(), "classes/paper");
                moduleSelector.getTargetItems().forEach(PluginModule::enable);
                mavenListView.getItems().forEach(MavenModule::enable);
            } catch (IOException | JSONException e) {
                Platform.runLater(() -> VisualBukkitApp.displayException(e));
            }
        });

        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.open(this);
        }

        JSONArray disabledPluginComponentsJson = data.optJSONArray("disabled-plugin-components");
        List<Object> disabledPluginComponents = disabledPluginComponentsJson != null ? disabledPluginComponentsJson.toList() : Collections.emptyList();
        if (Files.exists(pluginComponentDirectory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(pluginComponentDirectory)) {
                for (Path path : stream) {
                    PluginComponent pluginComponent = new PluginComponent(this, path);
                    pluginComponents.add(pluginComponent);
                    if (disabledPluginComponents.contains(pluginComponent.getName())) {
                        pluginComponent.setDisabled(true);
                    }
                }
            }
        }

        JSONArray openPluginComponentsJson = data.optJSONArray("open-plugin-components");
        if (openPluginComponentsJson != null) {
            for (Object obj : openPluginComponentsJson) {
                for (PluginComponent pluginComponent : pluginComponents) {
                    if (pluginComponent.getName().equals(obj)) {
                        openPluginComponent(pluginComponent);
                        break;
                    }
                }
            }
        }
        tabPane.getSelectionModel().select(data.optInt("selected-tab"));

        statementSelector.setStatements(BlockRegistry.getStatements());
        VisualBukkitApp.getRootPane().setCenter(projectPane);
        VisualBukkitApp.getData().put("current-project", getName());
        VisualBukkitApp.getPrimaryStage().setTitle("Visual Bukkit - " + getName());
    }

    public void save() throws IOException {
        data.remove("open-plugin-components");
        data.remove("disabled-plugin-components");
        data.remove("enabled-modules");
        data.remove("maven-repositories");
        data.remove("maven-dependencies");
        data.put("selected-tab", tabPane.getSelectionModel().getSelectedIndex());
        data.put("plugin-settings", pluginSettings.serialize());
        data.put("debug-mode", debugModeCheckBox.isSelected());
        data.put("jar-output", jarOutputField.getText());
        for (Tab tab : tabPane.getTabs()) {
            data.append("open-plugin-components", tab.getText());
        }
        for (PluginModule module : moduleSelector.getTargetItems()) {
            data.append("enabled-modules", module.getID());
        }
        for (MavenModule mavenModule : mavenListView.getItems()) {
            data.append(mavenModule instanceof MavenDependencyModule ? "maven-dependencies" : "maven-repositories", mavenModule.serialize());
        }
        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.save(this);
        }
        for (PluginComponent pluginComponent : pluginComponents) {
            pluginComponent.save();
            if (pluginComponent.isDisabled()) {
                data.append("disabled-plugin-components", pluginComponent.getName());
            }
        }
        Files.createDirectories(directory);
        Files.writeString(dataFile, data.toString(2));
    }

    public void openPluginComponent(PluginComponent pluginComponent) {
        tabPane.getSelectionModel().select(openPluginComponents.computeIfAbsent(pluginComponent, k -> {
            try {
                pluginComponent.load();
                Tab newTab = new Tab(k.getName(), pluginComponent.getPane());
                tabPane.getTabs().add(newTab);
                splitPane.getItems().set(1, tabPane);
                newTab.setOnClosed(e -> {
                    try {
                        openPluginComponents.remove(pluginComponent);
                        if (tabPane.getTabs().isEmpty()) {
                            splitPane.getItems().set(1, placeholderPane);
                        }
                        pluginComponent.unload();
                        VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.saved_plugin_component"));
                    } catch (IOException ex) {
                        VisualBukkitApp.displayException(ex);
                    }
                });
                return newTab;
            } catch (IOException e) {
                VisualBukkitApp.displayException(e);
                return null;
            }
        }));
    }

    public void showPluginComponents() {
        ListView<PluginComponent> listView = new ListView<>();
        listView.getItems().setAll(pluginComponents);
        listView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_plugin_components")));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Button addButton = new Button(VisualBukkitApp.localizedText("button.add"));
        Button importButton = new Button(VisualBukkitApp.localizedText("button.import"));
        Button exportButton = new Button(VisualBukkitApp.localizedText("button.export"));
        Button openButton = new Button(VisualBukkitApp.localizedText("button.open"));
        Button renameButton = new Button(VisualBukkitApp.localizedText("button.rename"));
        Button deleteButton = new Button(VisualBukkitApp.localizedText("button.delete"));
        VBox vBox = new VBox(listView, new HBox(addButton, importButton, exportButton, openButton, renameButton, deleteButton));
        vBox.getStyleClass().add("plugin-component-list");
        PopupWindow popupWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_components"), vBox);
        addButton.setOnAction(e -> {
            popupWindow.close();
            promptAddPluginComponent();
        });
        importButton.setOnAction(e -> {
            popupWindow.close();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(VisualBukkitApp.getPrimaryStage());
            if (file != null) {
                promptImportPluginComponent(file.toPath());
            }
        });
        exportButton.setOnAction(e -> {
            popupWindow.close();
            listView.getSelectionModel().getSelectedItems().forEach(this::promptExportPluginComponent);
        });
        openButton.setOnAction(e -> {
            popupWindow.close();
            listView.getSelectionModel().getSelectedItems().forEach(this::openPluginComponent);
        });
        renameButton.setOnAction(e -> {
            popupWindow.close();
            listView.getSelectionModel().getSelectedItems().forEach(this::promptRenamePluginComponent);
        });
        deleteButton.setOnAction(e -> {
            popupWindow.close();
            listView.getSelectionModel().getSelectedItems().forEach(this::promptDeletePluginComponent);
        });
        openButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        exportButton.disableProperty().bind(openButton.disableProperty());
        renameButton.disableProperty().bind(openButton.disableProperty());
        deleteButton.disableProperty().bind(openButton.disableProperty());
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PluginComponent> call(ListView<PluginComponent> param) {
                ListCell<PluginComponent> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(PluginComponent item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item != null ? item.getName() : "");
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

    public void promptAddPluginComponent() {
        TextField nameField = new TextField();
        SearchableComboBox<BlockFactory<PluginComponentBlock>> typeComboBox = new SearchableComboBox<>();
        typeComboBox.getItems().addAll(BlockRegistry.getPluginComponents());
        typeComboBox.getSelectionModel().selectFirst();
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("dialog.add_component_name")), nameField);
        gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("dialog.add_component_type")), typeComboBox);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_component"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK && isPluginComponentNameValid(nameField.getText())) {
                try {
                    PluginComponent pluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(nameField.getText()), typeComboBox.getValue().newBlock());
                    pluginComponent.save();
                    pluginComponents.add(pluginComponent);
                    openPluginComponent(pluginComponent);
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.added_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    public void promptDeletePluginComponent(PluginComponent pluginComponent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_delete"), pluginComponent.getName()));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    pluginComponent.delete();
                    pluginComponents.remove(pluginComponent);
                    tabPane.getTabs().remove(openPluginComponents.remove(pluginComponent));
                    if (tabPane.getTabs().isEmpty()) {
                        splitPane.getItems().set(1, placeholderPane);
                    }
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.deleted_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    public void promptDeletePluginComponent(PluginComponentBlock block) {
        for (PluginComponent pluginComponent : new HashSet<>(openPluginComponents.keySet())) {
            if (block.equals(pluginComponent.getBlock().orElse(null))) {
                promptDeletePluginComponent(pluginComponent);
                return;
            }
        }
    }

    public void promptExportPluginComponent(PluginComponent pluginComponent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showSaveDialog(VisualBukkitApp.getPrimaryStage());
        if (file != null) {
            try {
                Files.copy(pluginComponent.getFile(), file.toPath());
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.exported_plugin_component"));
            } catch (IOException ex) {
                VisualBukkitApp.displayException(ex);
            }
        }
    }

    public void promptImportPluginComponent(Path file) {
        TextInputDialog importDialog = new TextInputDialog();
        importDialog.setTitle(VisualBukkitApp.localizedText("window.import_plugin_component"));
        importDialog.setContentText(VisualBukkitApp.localizedText("dialog.import_plugin_component"));
        importDialog.setHeaderText(null);
        importDialog.setGraphic(null);
        importDialog.showAndWait().ifPresent(name -> {
            if (isPluginComponentNameValid(name)) {
                try {
                    Files.copy(file, pluginComponentDirectory.resolve(name));
                    PluginComponent pluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(name));
                    pluginComponents.add(pluginComponent);
                    openPluginComponent(pluginComponent);
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            } else {
                promptImportPluginComponent(file);
            }
        });
    }

    public void promptRenamePluginComponent(PluginComponent pluginComponent) {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle(VisualBukkitApp.localizedText("window.rename_plugin_component"));
        renameDialog.setContentText(VisualBukkitApp.localizedText("dialog.rename_plugin_component"));
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        renameDialog.showAndWait().ifPresent(name -> {
            if (isPluginComponentNameValid(name)) {
                try {
                    pluginComponent.save();
                    Files.move(pluginComponent.getFile(), pluginComponentDirectory.resolve(name));
                    pluginComponents.remove(pluginComponent);
                    tabPane.getTabs().remove(openPluginComponents.remove(pluginComponent));
                    PluginComponent renamedPluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(name));
                    pluginComponents.add(renamedPluginComponent);
                    openPluginComponent(renamedPluginComponent);
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.renamed_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            } else {
                promptRenamePluginComponent(pluginComponent);
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

    public void promptAddMavenDependency(MavenDependencyModule dependToEdit) {
        TextField groupIdField = new TextField();
        TextField artifactIdField = new TextField();
        TextField versionField = new TextField();
        SearchableComboBox<String> scopeField = new SearchableComboBox<>();
        scopeField.getItems().setAll(JavaScopes.COMPILE, JavaScopes.PROVIDED, JavaScopes.RUNTIME, JavaScopes.TEST, JavaScopes.SYSTEM);
        scopeField.setValue(JavaScopes.PROVIDED);
        if (dependToEdit != null) {
            groupIdField.setText(dependToEdit.getDependency().getArtifact().getGroupId());
            artifactIdField.setText(dependToEdit.getDependency().getArtifact().getArtifactId());
            versionField.setText(dependToEdit.getDependency().getArtifact().getVersion());
            scopeField.setValue(dependToEdit.getDependency().getScope());
        }
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("groupId"), groupIdField);
        gridPane.addRow(1, new Label("artifactId"), artifactIdField);
        gridPane.addRow(2, new Label("version"), versionField);
        gridPane.addRow(3, new Label("scope"), scopeField);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_maven_dependency"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (groupIdField.getText().isBlank() || artifactIdField.getText().isBlank() || versionField.getText().isBlank()) {
                    VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_maven_dependency"));
                    return;
                }
                if (dependToEdit != null) {
                    mavenListView.getItems().remove(dependToEdit);
                }
                DefaultArtifact artifact = new DefaultArtifact(String.format("%s:%s:%s", groupIdField.getText(), artifactIdField.getText(), versionField.getText()));
                mavenListView.getItems().add(new MavenDependencyModule(new Dependency(artifact, scopeField.getValue())));
                Collections.sort(mavenListView.getItems());
                reloadRequired = true;
            }
        });
    }

    public void promptAddMavenRepository(MavenRepositoryModule repoToEdit) {
        TextField idField = new TextField();
        TextField urlField = new TextField();
        if (repoToEdit != null) {
            idField.setText(repoToEdit.getRepository().getId());
            urlField.setText(repoToEdit.getRepository().getUrl());
        }
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("id"), idField);
        gridPane.addRow(1, new Label("url"), urlField);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_maven_repository"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (idField.getText().isBlank() || urlField.getText().isBlank()) {
                    VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_maven_repository"));
                    return;
                }
                if (repoToEdit != null) {
                    mavenListView.getItems().remove(repoToEdit);
                }
                RemoteRepository repository = new RemoteRepository.Builder(idField.getText(), "default", urlField.getText()).build();
                mavenListView.getItems().add(new MavenRepositoryModule(repository));
                Collections.sort(mavenListView.getItems());
                reloadRequired = true;
            }
        });
    }

    public CompletableFuture<Void> build() {
        VisualBukkitApp.getLogger().info("Building plugin...");
        VisualBukkitApp.getLogWindow().show();
        return BackgroundTaskExecutor.execute(() -> {
            try {
                String name = pluginSettings.getPluginName();
                String version = pluginSettings.getPluginVersion();
                if (name.isBlank()) {
                    name = "Plugin";
                }
                if (version.isBlank()) {
                    version = "1.0";
                }
                String packageName = "vb.$" + name.toLowerCase();

                Path mainDir = buildDirectory.resolve("src").resolve("main");
                Path packageDir = mainDir.resolve("java").resolve("vb").resolve("$" + name.toLowerCase());
                Path resourcesDir = mainDir.resolve("resources");
                if (Files.exists(buildDirectory)) {
                    MoreFiles.deleteRecursively(buildDirectory, RecursiveDeleteOption.ALLOW_INSECURE);
                }
                Files.createDirectories(packageDir);
                Files.createDirectories(resourcesDir);

                JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, Resources.toString(Project.class.getResource("/plugin/PluginMain.java"), StandardCharsets.UTF_8));
                mainClass.setPackage(packageName);

                if (Files.exists(resourcesDir)) {
                    try (Stream<Path> stream = Files.walk(resourcesDir)) {
                        for (Path path : stream.toArray(Path[]::new)) {
                            if (Files.isRegularFile(path) && !Files.isHidden(path)) {
                                Path relativePath = resourcesDirectory.relativize(path);
                                Path resourceDirPath = resourcesDir.resolve(relativePath);
                                Files.createDirectories(resourceDirPath.getParent());
                                Files.copy(path, resourceDirPath);
                                String filePath = StringEscapeUtils.escapeJava(relativePath.toString().replace("\\", "/"));
                                MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
                                enableMethod.setBody(enableMethod.getBody() + (filePath.equals("config.yml") ? "saveDefaultConfig();" : ("PluginMain.createResourceFile(\"" + filePath + "\");")));
                            }
                        }
                    }
                }

                BuildInfo buildInfo = new BuildInfo(mainClass, debugModeCheckBox.isSelected());
                for (PluginModule module : moduleSelector.getTargetItems()) {
                    module.prepareBuild(buildInfo);
                }
                for (MavenModule module : mavenListView.getItems()) {
                    module.prepareBuild(buildInfo);
                }

                StringBuilder commandsBuilder = new StringBuilder("commands:\n");
                for (PluginComponent pluginComponent : pluginComponents) {
                    if (pluginComponent.isDisabled()) {
                        continue;
                    }
                    PluginComponentBlock block = pluginComponent.load();
                    block.prepareBuild(buildInfo);
                    if (block instanceof CompCommand command && !command.getName().isBlank()) {
                        commandsBuilder.append("  ").append(command.getName()).append(":\n");
                        if (!command.getAliases().isBlank()) {
                            commandsBuilder.append("    aliases: [").append(command.getAliases()).append("]\n");
                        }
                        if (!command.getDescription().isBlank()) {
                            commandsBuilder.append("    description: \"").append(command.getDescription()).append("\"\n");
                        }
                        if (!command.getPermission().isBlank()) {
                            commandsBuilder.append("    permission: \"").append(command.getPermission()).append("\"\n");
                        }
                        if (!command.getPermissionMessage().isBlank()) {
                            commandsBuilder.append("    permission-message: \"").append(command.getPermissionMessage()).append("\"\n");
                        }
                        if (!command.getUsage().isBlank()) {
                            commandsBuilder.append("    usage: \"").append(command.getUsage()).append("\"\n");
                        }
                    }
                    pluginComponent.unload();
                }

                Files.writeString(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString());
                Files.writeString(buildDirectory.resolve("pom.xml"), MavenUtil.createPom(name.toLowerCase(), version, buildInfo));
                Files.writeString(resourcesDir.resolve("plugin.yml"), pluginSettings.createPluginYml(name, version, mainClass.getQualifiedName()) + "\n" + commandsBuilder);

                InvocationRequest request = new DefaultInvocationRequest();
                request.setOutputHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setErrorHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setBaseDirectory(buildDirectory.toFile());
                request.setGoals(Arrays.asList("clean", "package"));
                request.setBatchMode(true);
                InvocationResult result = MavenUtil.execute(request);
                if (result.getExecutionException() != null) {
                    VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to execute maven", result.getExecutionException());
                } else if (result.getExitCode() == 0 && !jarOutputField.getText().isBlank()) {
                    Path outputDir = Paths.get(jarOutputField.getText());
                    if (Files.exists(outputDir) && !outputDir.equals(buildDirectory.resolve("target"))) {
                        try (DirectoryStream<Path> stream = Files.newDirectoryStream(buildDirectory.resolve("target"), "*.jar")) {
                            for (Path path : stream) {
                                String fileName = path.getFileName().toString();
                                if (!fileName.startsWith("original-")) {
                                    Files.copy(path, outputDir.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to build plugin", e);
            }
        });
    }

    public boolean isOpen(PluginComponent pluginComponent) {
        return openPluginComponents.containsKey(pluginComponent);
    }

    public PluginComponent getOpenPluginComponent() {
        for (Map.Entry<PluginComponent, Tab> entry : openPluginComponents.entrySet()) {
            if (entry.getValue().equals(tabPane.getSelectionModel().getSelectedItem())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Set<PluginComponent> getPluginComponents() {
        return pluginComponents;
    }

    public String getName() {
        return directory.getFileName().toString();
    }

    public Path getDirectory() {
        return directory;
    }

    public JSONObject getData() {
        return data;
    }
}
