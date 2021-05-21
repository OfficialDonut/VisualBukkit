package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.extensions.ExtensionManager;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import com.gmail.visualbukkit.ui.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.ListSelectionView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Project {

    public static Set<PluginComponent> AVAILABLE_PLUGIN_COMPONENTS;

    private StringProperty pluginName = new SimpleStringProperty();
    private StringProperty pluginVersion = new SimpleStringProperty();
    private StringProperty pluginAuthor = new SimpleStringProperty();
    private StringProperty pluginDescription = new SimpleStringProperty();
    private StringProperty pluginWebsite = new SimpleStringProperty();
    private StringProperty pluginDependencies = new SimpleStringProperty();
    private StringProperty pluginSoftDependencies = new SimpleStringProperty();

    private Path dir;
    private Path dataFile;
    private Path resourcesDir;
    private Path buildDir;

    private BorderPane projectPane = new BorderPane();
    private Stage pluginSettingsStage = new Stage();
    private TabPane pluginComponentPane = new TabPane();
    private Map<Tab, PluginComponent.Block> pluginComponents = new HashMap<>();
    private ListSelectionView<VisualBukkitExtension> extensionView = new ListSelectionView<>();

    protected Project(Path dir) throws IOException {
        this.dir = dir;
        dataFile = dir.resolve("data.json");
        resourcesDir = dir.resolve("Resource Files");
        buildDir = dir.resolve("Build");
        Files.createDirectories(resourcesDir);
        Files.createDirectories(buildDir);

        TextField pluginNameField = new TextField();
        TextField pluginVerField = new TextField();
        TextField pluginAuthorField = new TextField();
        TextField pluginDescField = new TextField();
        TextField pluginWebsiteField = new TextField();
        TextField pluginDependField = new TextField();
        TextField pluginSoftDependField = new TextField();

        pluginNameField.textProperty().bindBidirectional(pluginName);
        pluginVerField.textProperty().bindBidirectional(pluginVersion);
        pluginAuthorField.textProperty().bindBidirectional(pluginAuthor);
        pluginDescField.textProperty().bindBidirectional(pluginDescription);
        pluginWebsiteField.textProperty().bindBidirectional(pluginWebsite);
        pluginDependField.textProperty().bindBidirectional(pluginDependencies);
        pluginSoftDependField.textProperty().bindBidirectional(pluginSoftDependencies);
        pluginNameField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !StringUtils.isAlphanumeric(newValue)) {
                pluginNameField.setText(oldValue);
            }
        });

        GridPane gridPane = new GridPane();
        VisualBukkitApp.getSettingsManager().bindStyle(gridPane);
        gridPane.getStyleClass().add("plugin-settings-grid");
        gridPane.addColumn(0, new Label(LanguageManager.get("label.plugin_name")),
                new Label(LanguageManager.get("label.plugin_version")), new Label(LanguageManager.get("label.plugin_author")),
                new Label(LanguageManager.get("label.plugin_description")), new Label(LanguageManager.get("label.plugin_website")),
                new Label(LanguageManager.get("label.plugin_depend")), new Label(LanguageManager.get("label.plugin_soft_depend")));
        gridPane.addColumn(1, pluginNameField, pluginVerField, pluginAuthorField, pluginDescField, pluginWebsiteField, pluginDependField, pluginSoftDependField);

        HBox buttonBar = new StyleableHBox();
        buttonBar.getStyleClass().add("project-button-bar");
        buttonBar.getChildren().addAll(
                new IconButton("add", LanguageManager.get("tooltip.add_component"), e -> promptAddPluginComponent()),
                new IconButton("settings", LanguageManager.get("tooltip.plugin_settings"), e -> pluginSettingsStage.show()),
                new IconButton("folder", LanguageManager.get("tooltip.resource_files"), e -> VisualBukkitApp.openDirectory(resourcesDir)),
                new IconButton("build", LanguageManager.get("tooltip.build_plugin"), e -> {
                    VisualBukkitApp.getLogger().show();
                    PluginBuilder.build(this);
                }),
                new IconButton("jar", LanguageManager.get("tooltip.build_directory"), e -> {
                    Path targetDir = buildDir.resolve("target");
                    if (Files.exists(targetDir)) {
                        VisualBukkitApp.openDirectory(targetDir);
                    } else {
                        NotificationManager.displayError(LanguageManager.get("error.cannot_open_build_dir.title"), LanguageManager.get("error.cannot_open_build_dir.content"));
                    }
                }),
                new IconButton("deploy", LanguageManager.get("tooltip.deploy_plugin"), e -> PluginBuilder.deploy(this)),
                new IconButton("log", LanguageManager.get("tooltip.log"), e -> VisualBukkitApp.getLogger().show()),
                new SpacerRegion(),
                new Label(String.format(LanguageManager.get("label.current_project"), getName())));

        Label componentPlaceholder = new Label(LanguageManager.get("label.add_plugin_component"));
        projectPane.setOnMouseClicked(e -> {
            if (projectPane.getCenter().equals(componentPlaceholder)) {
                promptAddPluginComponent();
            }
        });

        Button reloadButton = new Button(LanguageManager.get("button.reload_project"));
        reloadButton.setOnAction(e -> {
            ProjectManager.open(getName());
            NotificationManager.displayMessage(LanguageManager.get("message.reloaded_project.title"), LanguageManager.get("message.reloaded_project.content"));
        });

        projectPane.setBottom(buttonBar);
        projectPane.centerProperty().bind(Bindings.when(Bindings.isNotEmpty(pluginComponentPane.getTabs())).then((Node) pluginComponentPane).otherwise(componentPlaceholder));
        pluginSettingsStage.initModality(Modality.APPLICATION_MODAL);
        pluginSettingsStage.setTitle("Plugin Settings");
        pluginSettingsStage.setScene(new Scene(gridPane, 300, 300));
        pluginComponentPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        extensionView.getSourceItems().addAll(ExtensionManager.getExtensions());
        extensionView.setSourceFooter(reloadButton);

        JSONObject json = new JSONObject();

        if (Files.exists(dataFile)) {
            try {
                json = new JSONObject(Files.readString(dataFile));
            } catch (JSONException ignored) {}
        }

        pluginName.set(json.optString("plugin.name", ""));
        pluginVersion.set(json.optString("plugin.version", ""));
        pluginAuthor.set(json.optString("plugin.author", ""));
        pluginDescription.set(json.optString("plugin.description", ""));
        pluginWebsite.set(json.optString("plugin.website", ""));
        pluginDependencies.set(json.optString("plugin.dependencies", ""));
        pluginSoftDependencies.set(json.optString("plugin.soft-dependencies", ""));

        JSONArray extensionArray = json.optJSONArray("extensions");
        if (extensionArray != null) {
            for (Object obj : extensionArray) {
                if (obj instanceof String) {
                    VisualBukkitExtension extension = ExtensionManager.getExtension((String) obj);
                    if (extension != null) {
                        extensionView.getSourceItems().remove(extension);
                        extensionView.getTargetItems().add(extension);
                    }
                }
            }
        }

        BlockRegistry.setActiveExtensions(extensionView.getTargetItems());

        JSONArray componentArray = json.optJSONArray("plugin-components");
        if (componentArray != null) {
            for (Object obj : componentArray) {
                if (obj instanceof JSONObject) {
                    JSONObject componentJson = (JSONObject) obj;
                    PluginComponent component = BlockRegistry.getPluginComponent(componentJson.optString("="));
                    if (component != null) {
                        addPluginComponent(component.createBlock(componentJson));
                    }
                }
            }
        }

        int openTabIndex = json.optInt("open-tab");
        if (openTabIndex >= 0 && openTabIndex < pluginComponentPane.getTabs().size()) {
            pluginComponentPane.getSelectionModel().select(openTabIndex);
        }
    }

    public void save() throws IOException {
        JSONObject json = new JSONObject();
        json.put("plugin.name", getPluginName());
        json.put("plugin.version", getPluginVersion());
        json.put("plugin.author", getPluginAuthor());
        json.put("plugin.description", getPluginDescription());
        json.put("plugin.website", getPluginWebsite());
        json.put("plugin.dependencies", getPluginDependencies());
        json.put("plugin.soft-dependencies", getPluginSoftDependencies());
        json.put("open-tab", pluginComponentPane.getSelectionModel().getSelectedIndex());

        for (VisualBukkitExtension extension : extensionView.getTargetItems()) {
            json.append("extensions", extension.getName());
        }

        for (Tab tab : pluginComponentPane.getTabs()) {
            json.append("plugin-components", pluginComponents.get(tab).serialize());
        }

        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
        Files.writeString(dataFile, json.toString(2));
    }

    public void addPluginComponent(PluginComponent.Block block) {
        Tab tab = block.getTab();
        tab.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LanguageManager.get("dialog.confirm_delete_component"), ButtonType.YES, ButtonType.CANCEL);
            VisualBukkitApp.getSettingsManager().style(alert.getDialogPane());
            alert.setHeaderText(null);
            alert.setGraphic(null);
            if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.YES) {
                int index = pluginComponentPane.getTabs().indexOf(tab);
                Platform.runLater(() -> {
                    pluginComponentPane.getTabs().add(index, tab);
                    pluginComponentPane.getSelectionModel().select(tab);
                });
            } else {
                pluginComponents.remove(tab);
            }
        });
        pluginComponents.put(tab, block);
        pluginComponentPane.getTabs().add(tab);
        pluginComponentPane.getSelectionModel().selectLast();
    }

    public void promptAddPluginComponent() {
        ChoiceDialog<PluginComponent> addComponentDialog = new ChoiceDialog<>();
        VisualBukkitApp.getSettingsManager().style(addComponentDialog.getDialogPane());
        addComponentDialog.getItems().addAll(AVAILABLE_PLUGIN_COMPONENTS);
        addComponentDialog.setTitle(LanguageManager.get("dialog.add_component.title"));
        addComponentDialog.setContentText(LanguageManager.get("dialog.add_component.content"));
        addComponentDialog.setHeaderText(null);
        addComponentDialog.setGraphic(null);
        addComponentDialog.showAndWait().ifPresent(pluginComponent -> addPluginComponent(pluginComponent.createBlock()));
    }

    public void promptImportComponent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(VisualBukkitApp.getStage());
        if (file != null) {
            try {
                JSONObject json = new JSONObject(Files.readString(file.toPath()));
                PluginComponent component = BlockRegistry.getPluginComponent(json.optString("="));
                if (component != null) {
                    addPluginComponent(component.createBlock(json));
                }
            } catch (IOException e) {
                NotificationManager.displayException("Failed to import component", e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void promptExportComponent() {
        ChoiceDialog<Tab> exportDialog = new ChoiceDialog<>();
        VisualBukkitApp.getSettingsManager().style(exportDialog.getDialogPane());
        exportDialog.getItems().addAll(pluginComponentPane.getTabs());
        exportDialog.setTitle(LanguageManager.get("dialog.export_component.title"));
        exportDialog.setContentText(LanguageManager.get("dialog.export_component.content"));
        exportDialog.setHeaderText(null);
        exportDialog.setGraphic(null);

        ComboBox<Tab> comboBox = (ComboBox<Tab>) ((GridPane) exportDialog.getDialogPane().getContent()).getChildren().get(1);
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Tab tab) {
                return tab != null ? tab.getText() : null;
            }
            @Override
            public Tab fromString(String string) {
                return null;
            }
        });

        exportDialog.showAndWait().ifPresent(tab -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File outputDir = directoryChooser.showDialog(VisualBukkitApp.getStage());
            if (outputDir != null) {
                try {
                    Files.writeString(outputDir.toPath().resolve(UUID.randomUUID() + ".json"), pluginComponents.get(tab).serialize().toString(2));
                    VisualBukkitApp.openDirectory(outputDir.toPath());
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to export component", e);
                }
            }
        });
    }

    public String getName() {
        return dir.getFileName().toString();
    }

    public Path getDir() {
        return dir;
    }

    public Path getBuildDir() {
        return buildDir;
    }

    public Path getResourcesDir() {
        return resourcesDir;
    }

    public BorderPane getProjectPane() {
        return projectPane;
    }

    public Collection<PluginComponent.Block> getPluginComponents() {
        return pluginComponents.values();
    }

    public Stage getPluginSettingsStage() {
        return pluginSettingsStage;
    }

    public TabPane getPluginComponentPane() {
        return pluginComponentPane;
    }

    public ListSelectionView<VisualBukkitExtension> getExtensionView() {
        return extensionView;
    }

    public String getPluginName() {
        return pluginName.get();
    }

    public String getPluginVersion() {
        return pluginVersion.get();
    }

    public String getPluginAuthor() {
        return pluginAuthor.get();
    }

    public String getPluginDescription() {
        return pluginDescription.get();
    }

    public String getPluginWebsite() {
        return pluginWebsite.get();
    }

    public String getPluginDependencies() {
        return pluginDependencies.get();
    }

    public String getPluginSoftDependencies() {
        return pluginSoftDependencies.get();
    }
}
