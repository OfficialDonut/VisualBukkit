package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.DataFile;
import com.gmail.visualbukkit.SettingsManager;
import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.gui.NotificationManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Project {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty version = new SimpleStringProperty();
    private StringProperty author = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty dependencies = new SimpleStringProperty();
    private StringProperty softDependencies = new SimpleStringProperty();
    private Stage pluginSettingsStage = new Stage();
    private TabPane pluginComponentTabPane = new TabPane();
    private Map<Tab, PluginComponent.Block> pluginComponents = new HashMap<>();
    private Path dir;
    private Path resourceDir;
    private Path buildDir;
    private DataFile dataFile;

    public Project(Path directory) throws IOException {
        this.dir = directory;
        resourceDir = directory.resolve("Resource Files");
        buildDir = directory.resolve("Build");
        dataFile = new DataFile(directory.resolve("data.json"));
        Files.createDirectories(resourceDir);
        Files.createDirectories(buildDir);

        TextField nameField = new TextField();
        TextField verField = new TextField();
        TextField authorField = new TextField();
        TextField descField = new TextField();
        TextField dependField = new TextField();
        TextField softDependField = new TextField();
        nameField.textProperty().bindBidirectional(name);
        verField.textProperty().bindBidirectional(version);
        authorField.textProperty().bindBidirectional(author);
        descField.textProperty().bindBidirectional(description);
        dependField.textProperty().bindBidirectional(dependencies);
        softDependField.textProperty().bindBidirectional(softDependencies);
        nameField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !StringUtils.isAlphanumeric(newValue)) {
                nameField.setText(oldValue);
            }
        });
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("plugin-settings-grid");
        gridPane.addColumn(0,
                new Label(VisualBukkitApp.getString("label.plugin_name")), new Label(VisualBukkitApp.getString("label.plugin_version")),
                new Label(VisualBukkitApp.getString("label.plugin_author")), new Label(VisualBukkitApp.getString("label.plugin_description")),
                new Label(VisualBukkitApp.getString("label.plugin_depend")), new Label(VisualBukkitApp.getString("label.plugin_soft_depend")));
        gridPane.addColumn(1, nameField, verField, authorField, descField, dependField, softDependField);
        SettingsManager.getInstance().bindStyle(gridPane);
        pluginSettingsStage.initModality(Modality.APPLICATION_MODAL);
        pluginSettingsStage.setTitle(VisualBukkitApp.getString("window.plugin_settings"));
        pluginSettingsStage.setScene(new Scene(gridPane, 300, 300));

        pluginComponentTabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);

        JSONObject json = dataFile.getJson();
        name.set(json.optString("plugin.name", ""));
        version.set(json.optString("plugin.version", ""));
        author.set(json.optString("plugin.author", ""));
        description.set(json.optString("plugin.description", ""));
        dependencies.set(json.optString("plugin.dependencies", ""));
        softDependencies.set(json.optString("plugin.soft-dependencies", ""));
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
        if (openTabIndex >= 0 && openTabIndex < pluginComponentTabPane.getTabs().size()) {
            pluginComponentTabPane.getSelectionModel().select(openTabIndex);
        }
    }

    public void save() throws IOException {
        dataFile.clear();
        JSONObject json = dataFile.getJson();
        json.put("plugin.name", getName());
        json.put("plugin.version", getVersion());
        json.put("plugin.author", getAuthor());
        json.put("plugin.description", getDescription());
        json.put("plugin.dependencies", getDependencies());
        json.put("plugin.soft-dependencies", getSoftDependencies());
        json.put("open-tab", pluginComponentTabPane.getSelectionModel().getSelectedIndex());
        for (Tab tab : pluginComponentTabPane.getTabs()) {
            json.append("plugin-components", pluginComponents.get(tab).serialize());
        }
        dataFile.save();
    }

    public void promptImportComponent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(VisualBukkitApp.getInstance().getPrimaryStage());
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
        exportDialog.getItems().addAll(pluginComponentTabPane.getTabs());
        exportDialog.setTitle(VisualBukkitApp.getString("dialog.export_component.title"));
        exportDialog.setContentText(VisualBukkitApp.getString("dialog.export_component.content"));
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
            File outputDir = directoryChooser.showDialog(VisualBukkitApp.getInstance().getPrimaryStage());
            if (outputDir != null) {
                try {
                    Files.writeString(outputDir.toPath().resolve(UUID.randomUUID().toString() + ".json"), pluginComponents.get(tab).serialize().toString(2));
                    VisualBukkitApp.getInstance().openDirectory(outputDir.toPath());
                } catch (IOException e) {
                    NotificationManager.displayException("Failed to export component", e);
                }
            }
        });
    }

    public void promptAddPluginComponent() {
        ComboBox<PluginComponent> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(BlockRegistry.getPluginComponents());
        HBox content = new HBox(5, new Label(VisualBukkitApp.getString("dialog.add_component.content")), comboBox);
        content.setAlignment(Pos.CENTER_LEFT);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(VisualBukkitApp.getString("dialog.add_component.title"));
        alert.getDialogPane().setContent(content);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                PluginComponent pluginComponent = comboBox.getValue();
                if (pluginComponent != null) {
                    addPluginComponent(pluginComponent.createBlock());
                }
            }
        });
    }

    public void addPluginComponent(PluginComponent.Block block) {
        Tab tab = block.getTab();
        tab.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, VisualBukkitApp.getString("dialog.confirm_delete_component"), ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText(null);
            alert.setGraphic(null);
            if (alert.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.YES) {
                int index = pluginComponentTabPane.getTabs().indexOf(tab);
                Platform.runLater(() -> {
                    pluginComponentTabPane.getTabs().add(index, tab);
                    pluginComponentTabPane.getSelectionModel().select(tab);
                });
            }
        });
        pluginComponents.put(tab, block);
        pluginComponentTabPane.getTabs().add(tab);
        pluginComponentTabPane.getSelectionModel().selectLast();
    }

    public Path getDir() {
        return dir;
    }

    public Path getBuildDir() {
        return buildDir;
    }

    public Path getResourceDir() {
        return resourceDir;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public Stage getPluginSettingsStage() {
        return pluginSettingsStage;
    }

    public TabPane getPluginComponentTabPane() {
        return pluginComponentTabPane;
    }

    public Collection<PluginComponent.Block> getPluginComponents() {
        return Collections.unmodifiableCollection(pluginComponents.values());
    }

    public String getName() {
        return name.get();
    }

    public String getVersion() {
        return version.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getDependencies() {
        return dependencies.get();
    }

    public String getSoftDependencies() {
        return softDependencies.get();
    }
}
