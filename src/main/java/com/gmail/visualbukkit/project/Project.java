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
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.io.Resources;
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
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
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
    private final Map<String, PluginComponentPane> openPluginComponents = new HashMap<>();

    public Project(Path directory) {
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        pluginComponentDirectory = directory.resolve("plugin_components");
        resourcesDirectory = directory.resolve("resource_files");
        buildDirectory = directory.resolve("build");

        moduleSelector.getTargetItems().addListener((ListChangeListener<PluginModule>) c -> reloadRequired = true);
        moduleSelector.setSourceHeader(new Label(VisualBukkitApp.localizedText("label.disabled_modules")));
        moduleSelector.setTargetHeader(new Label(VisualBukkitApp.localizedText("label.enabled_modules")));
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
        TabPane settingsTabPane = new TabPane(pluginYmlTab, modulesTab, mavenTab);
        settingsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        PopupWindow pluginSettingsWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_settings"), settingsTabPane);
        pluginSettingsWindow.setOnHidden(e -> {
            if (reloadRequired) {
                reloadRequired = false;
                ProjectManager.open(getName());
            }
        });

        HBox buttonBar = new HBox(
                new ActionButton(VisualBukkitApp.localizedText("button.add_component"), e -> promptAddPluginComponent()),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_components"), e -> showPluginComponents()),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_settings"), e -> pluginSettingsWindow.show()),
                new ActionButton(VisualBukkitApp.localizedText("button.build_plugin"), e -> build()));
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

        pluginSettings.deserialize(data.optJSONObject("plugin-settings", new JSONObject()));

        JSONArray enabledModulesJson = data.optJSONArray("enabled-modules");
        List<Object> enabledModules = enabledModulesJson != null ? enabledModulesJson.toList() : Collections.emptyList();
        for (PluginModule module : PluginModuleRegistry.getPluginModules()) {
            (enabledModules.contains(module.getUID()) ? moduleSelector.getTargetItems() : moduleSelector.getSourceItems()).add(module);
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

        JSONArray openPluginComponents = data.optJSONArray("open-plugin-components");
        if (openPluginComponents != null) {
            for (Object object : openPluginComponents) {
                if (object instanceof String name) {
                    openPluginComponent(name);
                }
            }
            tabPane.getSelectionModel().select(data.optInt("selected-tab"));
        }

        statementSelector.setStatements(BlockRegistry.getStatements());
        VisualBukkitApp.getRootPane().setCenter(projectPane);
        VisualBukkitApp.getData().put("current-project", getName());
        VisualBukkitApp.getPrimaryStage().setTitle("Visual Bukkit - " + getName());
    }

    public void save() throws IOException {
        data.remove("open-plugin-components");
        data.remove("enabled-modules");
        data.remove("maven-repositories");
        data.remove("maven-dependencies");
        data.put("selected-tab", tabPane.getSelectionModel().getSelectedIndex());
        data.put("plugin-settings", pluginSettings.serialize());
        for (Tab tab : tabPane.getTabs()) {
            data.append("open-plugin-components", tab.getText());
        }
        for (PluginModule module : moduleSelector.getTargetItems()) {
            data.append("enabled-modules", module.getUID());
        }
        for (MavenModule mavenModule : mavenListView.getItems()) {
            data.append(mavenModule instanceof MavenDependencyModule ? "maven-dependencies" : "maven-repositories", mavenModule.serialize());
        }
        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.save(this);
        }
        Files.createDirectories(directory);
        Files.writeString(dataFile, data.toString(2));
        for (Map.Entry<String, PluginComponentPane> entry : openPluginComponents.entrySet()) {
            savePluginComponent(entry.getKey(), entry.getValue().getBlock());
        }
    }

    private void savePluginComponent(String name, PluginComponentBlock block) throws IOException {
        Files.createDirectories(pluginComponentDirectory);
        Files.writeString(pluginComponentDirectory.resolve(name), block.serialize().toString());
    }

    private PluginComponentBlock loadPluginComponent(String name) throws IOException, JSONException {
        PluginComponentBlock block = BlockRegistry.newPluginComponent(new JSONObject(Files.readString(pluginComponentDirectory.resolve(name))));
        block.updateState();
        return block;
    }

    public Set<PluginComponentBlock> loadAllPluginComponents() throws IOException {
        Set<PluginComponentBlock> blocks = openPluginComponents.values().stream().map(PluginComponentPane::getBlock).collect(Collectors.toSet());
        if (Files.exists(pluginComponentDirectory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(pluginComponentDirectory)) {
                for (Path path : stream) {
                    String name = path.getFileName().toString();
                    if (!openPluginComponents.containsKey(name)) {
                        blocks.add(loadPluginComponent(name));
                    }
                }
            }
        }
        return blocks;
    }

    private void openPluginComponent(String name) {
        if (!openPluginComponents.containsKey(name)) {
            try {
                addPluginComponent(name, loadPluginComponent(name));
            } catch (IOException | JSONException e) {
                VisualBukkitApp.displayException(e);
            }
        }
        for (Tab tab : tabPane.getTabs()) {
            if (name.equals(tab.getText())) {
                tabPane.getSelectionModel().select(tab);
                return;
            }
        }
    }

    private void addPluginComponent(String name, PluginComponentBlock block) {
        PluginComponentPane pluginComponentPane = new PluginComponentPane(block);
        Tab tab = new Tab(name, pluginComponentPane);
        openPluginComponents.put(name, pluginComponentPane);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        splitPane.getItems().set(1, tabPane);
        tab.setOnClosed(e -> {
            try {
                savePluginComponent(name, block);
                openPluginComponents.remove(name);
                if (tabPane.getTabs().isEmpty()) {
                    splitPane.getItems().set(1, placeholderPane);
                }
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.plugin_component_saved"));
            } catch (IOException ex) {
                VisualBukkitApp.displayException(ex);
            }
        });
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

    public void promptDeletePluginComponent(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_delete"), name));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    Files.deleteIfExists(pluginComponentDirectory.resolve(name));
                    openPluginComponents.remove(name);
                    tabPane.getTabs().removeIf(tab -> name.equals(tab.getText()));
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

    public void build() {
        VisualBukkitApp.getLogger().info("Building plugin...");
        VisualBukkitApp.getLogWindow().show();
        BackgroundTaskExecutor.executeAndWait(() -> {
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

                BuildInfo buildInfo = new BuildInfo(mainClass);
                for (PluginModule module : moduleSelector.getTargetItems()) {
                    module.prepareBuild(buildInfo);
                }
                for (MavenModule module : mavenListView.getItems()) {
                    module.prepareBuild(buildInfo);
                }

                StringBuilder commandsBuilder = new StringBuilder("commands:\n");
                for (PluginComponentBlock block : loadAllPluginComponents()) {
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
                MavenUtil.execute(request);
            } catch (Exception e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to build plugin", e);
            }
        });
    }

    public Tab getOpenTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

    public PluginComponentPane getOpenPluginComponent() {
        return getOpenTab() != null ? (PluginComponentPane) getOpenTab().getContent() : null;
    }

    public String getName() {
        return directory.getFileName().toString();
    }

    public Path getDirectory() {
        return directory;
    }
}
