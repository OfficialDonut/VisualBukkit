package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.VisualBukkitExtension;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.BackgroundTaskExecutor;
import com.gmail.visualbukkit.ui.PopupWindow;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.action.Action;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class Project {

    private final Path directory;
    private final Path dataFile;
    private final Path pluginComponentDirectory;
    private final Path resourcesDirectory;
    private final Path buildDirectory;
    private JSONObject data;

    private final BorderPane projectPane = new BorderPane();
    private final SplitPane splitPane = new SplitPane();
    private final VBox placeholderPane = new VBox();
    private final TabPane tabPane = new TabPane();
    private final StatementSelector statementSelector = new StatementSelector();
    private final ListView<MavenModule> mavenListView = new ListView<>();
    private final ListSelectionView<PluginModule> moduleSelector = new ListSelectionView<>();
    private final Map<String, PluginComponentBlock> pluginComponents = new HashMap<>();

    private final TextField pluginNameField = new TextField();
    private final TextField pluginVersionField = new TextField();
    private final TextField pluginDescriptionField = new TextField();
    private final TextField pluginAuthorsField = new TextField();
    private final TextField pluginPrefixField = new TextField();
    private final TextField pluginWebsiteField = new TextField();
    private final TextField pluginDependenciesField = new TextField();
    private final TextField pluginSoftDependField = new TextField();
    private final TextField pluginLoadBeforeField = new TextField();
    private final TextArea pluginPermissionsField = new TextArea();

    private boolean reloadRequired;

    public Project(Path directory) {
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        pluginComponentDirectory = directory.resolve("plugin_components");
        resourcesDirectory = directory.resolve("resource_files");
        buildDirectory = directory.resolve("build");

        GridPane pluginSettingsGrid = new GridPane();
        pluginSettingsGrid.addRow(0, new Label(VisualBukkitApp.localizedText("label.plugin_settings_name")), pluginNameField);
        pluginSettingsGrid.addRow(1, new Label(VisualBukkitApp.localizedText("label.plugin_settings_version")), pluginVersionField);
        pluginSettingsGrid.addRow(2, new Label(VisualBukkitApp.localizedText("label.plugin_settings_description")), pluginDescriptionField);
        pluginSettingsGrid.addRow(3, new Label(VisualBukkitApp.localizedText("label.plugin_settings_authors")), pluginAuthorsField);
        pluginSettingsGrid.addRow(4, new Label(VisualBukkitApp.localizedText("label.plugin_settings_prefix")), pluginPrefixField);
        pluginSettingsGrid.addRow(5, new Label(VisualBukkitApp.localizedText("label.plugin_settings_website")), pluginWebsiteField);
        pluginSettingsGrid.addRow(6, new Separator(), new Separator());
        pluginSettingsGrid.addRow(7, new Label(VisualBukkitApp.localizedText("label.plugin_settings_dependencies")), pluginDependenciesField);
        pluginSettingsGrid.addRow(8, new Label(VisualBukkitApp.localizedText("label.plugin_settings_softdepend")), pluginSoftDependField);
        pluginSettingsGrid.addRow(9, new Label(VisualBukkitApp.localizedText("label.plugin_settings_loadbefore")), pluginLoadBeforeField);
        pluginSettingsGrid.addRow(10, new Label(VisualBukkitApp.localizedText("label.plugin_settings_permissions")), pluginPermissionsField);
        pluginSettingsGrid.getStyleClass().add("plugin-settings-grid");

        Button addDependButton = new Button(VisualBukkitApp.localizedText("button.add_dependency"));
        Button addRepoButton = new Button(VisualBukkitApp.localizedText("button.add_repository"));
        Button editButton = new Button(VisualBukkitApp.localizedText("button.edit"));
        Button deleteButton = new Button(VisualBukkitApp.localizedText("button.delete"));
        addDependButton.setOnAction(e -> promptAddMavenDependency(null));
        addRepoButton.setOnAction(e -> promptAddMavenRepository(null));
        editButton.setOnAction(e -> {
            MavenModule selectedMavenModule = mavenListView.getSelectionModel().getSelectedItem();
            if (selectedMavenModule instanceof MavenDependencyModule mavenDependency) {
                promptAddMavenDependency(mavenDependency);
            } else if (selectedMavenModule instanceof MavenRepositoryModule mavenRepository) {
                promptAddMavenRepository(mavenRepository);
            }
        });
        deleteButton.setOnAction(e -> {
            mavenListView.getItems().remove(mavenListView.getSelectionModel().getSelectedItem());
            reloadRequired = true;
        });
        editButton.disableProperty().bind(mavenListView.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.disableProperty().bind(editButton.disableProperty());
        VBox mavenPane = new VBox(new Label(VisualBukkitApp.localizedText("label.maven_settings")), mavenListView, new HBox(addDependButton, addRepoButton, editButton, deleteButton));
        mavenPane.getStyleClass().add("maven-settings-pane");

        moduleSelector.getTargetItems().addListener((ListChangeListener<PluginModule>) c -> reloadRequired = true);
        moduleSelector.setSourceHeader(new Label(VisualBukkitApp.localizedText("label.disabled_modules")));
        moduleSelector.setTargetHeader(new Label(VisualBukkitApp.localizedText("label.enabled_modules")));
        for (Action action : moduleSelector.getActions()) {
            action.graphicProperty().unbind();
            action.setGraphic(null);
            action.setText(action instanceof ListSelectionView.MoveToTarget ? ">" : action instanceof ListSelectionView.MoveToTargetAll ? ">>" : action instanceof ListSelectionView.MoveToSource ? "<" : "<<");
        }

        Tab pluginYmlTab = new Tab(VisualBukkitApp.localizedText("label.plugin_attributes"), pluginSettingsGrid);
        Tab mavenTab = new Tab(VisualBukkitApp.localizedText("label.maven"), mavenPane);
        Tab modulesTab = new Tab(VisualBukkitApp.localizedText("label.extension_modules"), moduleSelector);
        TabPane settingsTabPane = new TabPane(pluginYmlTab, mavenTab, modulesTab);
        PopupWindow pluginSettingsWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_settings"), settingsTabPane);
        pluginSettingsWindow.setOnHidden(e -> {
            if (reloadRequired) {
                reloadRequired = false;
                save();
                open();
            }
        });

        Button addComponentButton = new Button(VisualBukkitApp.localizedText("button.add_component"));
        Button pluginComponentsButton = new Button(VisualBukkitApp.localizedText("button.plugin_components"));
        Button pluginSettingsButton = new Button(VisualBukkitApp.localizedText("button.plugin_settings"));
        Button buildPluginButton = new Button(VisualBukkitApp.localizedText("button.build_plugin"));
        addComponentButton.setOnAction(e -> promptAddPluginComponent());
        pluginComponentsButton.setOnAction(e -> showPluginComponents());
        pluginSettingsButton.setOnAction(e -> pluginSettingsWindow.show());
        buildPluginButton.setOnAction(e -> PluginBuilder.build(this));
        HBox buttonBar = new HBox(addComponentButton, pluginComponentsButton, pluginSettingsButton, buildPluginButton);
        buttonBar.getStyleClass().add("button-bar");

        placeholderPane.getChildren().add(new Label(VisualBukkitApp.localizedText("label.add_component")));
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
        tabPane.getTabs().clear();
        pluginComponents.clear();
        mavenListView.getItems().clear();
        moduleSelector.getSourceItems().clear();
        moduleSelector.getTargetItems().clear();
        BlockRegistry.clear();
        ClassRegistry.clear();
        UndoManager.clear();

        BackgroundTaskExecutor.executeAndWait(() -> {
            try {
                if (Files.exists(dataFile)) {
                    data = new JSONObject(Files.readString(dataFile));
                }
                BlockRegistry.register(BlockRegistry.class.getClassLoader(), "com.gmail.visualbukkit.blocks.definitions");
                ClassRegistry.register(ClassRegistry.class.getClassLoader(), "classes/jdk");
                ClassRegistry.register(ClassRegistry.class.getClassLoader(), "classes/paper");
            } catch (IOException | JSONException e) {
                Platform.runLater(() -> VisualBukkitApp.displayException(e));
            }
            JSONArray enabledModulesJson = data.optJSONArray("enabled-modules");
            List<Object> enabledModules = enabledModulesJson != null ? enabledModulesJson.toList() : Collections.emptyList();
            for (PluginModule module : PluginModuleRegistry.getPluginModules()) {
                if (enabledModules.contains(module.getUID())) {
                    module.enable();
                    Platform.runLater(() -> moduleSelector.getTargetItems().add(module));
                } else {
                    Platform.runLater(() -> moduleSelector.getSourceItems().add(module));
                }
            }
            JSONArray mavenRepoJson = data.optJSONArray("maven-repositories");
            if (mavenRepoJson != null) {
                for (Object o : mavenRepoJson) {
                    MavenRepositoryModule mavenRepository = MavenRepositoryModule.deserialize((JSONObject) o);
                    mavenRepository.enable();
                    Platform.runLater(() -> mavenListView.getItems().add(mavenRepository));
                }
            }
            JSONArray mavenDependJson = data.optJSONArray("maven-dependencies");
            if (mavenDependJson != null) {
                for (Object o : mavenDependJson) {
                    MavenDependencyModule mavenDependency = MavenDependencyModule.deserialize((JSONObject) o);
                    mavenDependency.enable();
                    Platform.runLater(() -> mavenListView.getItems().add(mavenDependency));
                }
            }
        });

        pluginNameField.setText(data.optString("plugin-settings.name", ""));
        pluginVersionField.setText(data.optString("plugin-settings.version", ""));
        pluginDescriptionField.setText(data.optString("plugin-settings.description", ""));
        pluginAuthorsField.setText(data.optString("plugin-settings.authors", ""));
        pluginPrefixField.setText(data.optString("plugin-settings.prefix", ""));
        pluginWebsiteField.setText(data.optString("plugin-settings.website", ""));
        pluginDependenciesField.setText(data.optString("plugin-settings.dependencies", ""));
        pluginSoftDependField.setText(data.optString("plugin-settings.soft-depend", ""));
        pluginLoadBeforeField.setText(data.optString("plugin-settings.load-before", ""));
        pluginPermissionsField.setText(data.optString("plugin-settings.permissions", ""));

        JSONArray openPluginComponents = data.optJSONArray("open-plugin-components");
        if (openPluginComponents != null) {
            for (Object object : openPluginComponents) {
                if (object instanceof String name) {
                    openPluginComponent(name);
                }
            }
            tabPane.getSelectionModel().select(data.optInt("selected-tab"));
        }

        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.open(this);
        }

        statementSelector.refreshStatements();
        VisualBukkitApp.getRootPane().setCenter(projectPane);
        VisualBukkitApp.getData().put("current-project", getName());
    }

    public void save() {
        try {
            data.put("plugin-settings.name", pluginNameField.getText());
            data.put("plugin-settings.version", pluginVersionField.getText());
            data.put("plugin-settings.description", pluginDescriptionField.getText());
            data.put("plugin-settings.authors", pluginAuthorsField.getText());
            data.put("plugin-settings.prefix", pluginPrefixField.getText());
            data.put("plugin-settings.website", pluginWebsiteField.getText());
            data.put("plugin-settings.dependencies", pluginDependenciesField.getText());
            data.put("plugin-settings.soft-depend", pluginSoftDependField.getText());
            data.put("plugin-settings.load-before", pluginLoadBeforeField.getText());
            data.put("plugin-settings.permissions", pluginPermissionsField.getText());
            data.put("selected-tab", tabPane.getSelectionModel().getSelectedIndex());
            data.remove("open-plugin-components");
            for (Tab tab : tabPane.getTabs()) {
                data.append("open-plugin-components", tab.getText());
            }
            data.remove("enabled-modules");
            for (PluginModule module : moduleSelector.getTargetItems()) {
                data.append("enabled-modules", module.getUID());
            }
            for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
                extension.save(this);
            }
            data.remove("maven-repositories");
            data.remove("maven-dependencies");
            for (MavenModule mavenModule : mavenListView.getItems()) {
                data.append(mavenModule instanceof MavenDependencyModule ? "maven-dependencies" : "maven-repositories", mavenModule.serialize());
            }
            Files.createDirectories(directory);
            Files.writeString(dataFile, data.toString(2));
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
        VBox vBox = new VBox(listView, new HBox(addButton, openAllButton, openButton, deleteButton));
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
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_component"));
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

    public String getPluginName() {
        return pluginNameField.getText();
    }

    public String getPluginVersion() {
        return pluginVersionField.getText();
    }

    public String getPluginDescription() {
        return pluginDescriptionField.getText();
    }

    public String getPluginAuthors() {
        return pluginAuthorsField.getText();
    }

    public String getPluginPrefix() {
        return pluginPrefixField.getText();
    }

    public String getPluginWebsite() {
        return pluginWebsiteField.getText();
    }

    public String getPluginDependencies() {
        return pluginDependenciesField.getText();
    }

    public String getPluginSoftDepend() {
        return pluginSoftDependField.getText();
    }

    public String getPluginLoadBefore() {
        return pluginLoadBeforeField.getText();
    }

    public String getPluginPermissions() {
        return pluginPermissionsField.getText();
    }
}
