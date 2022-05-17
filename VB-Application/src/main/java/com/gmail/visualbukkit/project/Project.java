package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.definitions.CompCommand;
import com.gmail.visualbukkit.blocks.generated.EventComponent;
import com.gmail.visualbukkit.extensions.ExtensionManager;
import com.gmail.visualbukkit.extensions.VisualBukkitExtension;
import com.gmail.visualbukkit.ui.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
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

    private Path dir;
    private Path dataFile;
    private Path resourcesDir;
    private Path buildDir;
    private JSONObject data;

    private List<PluginComponent.Block> pluginComponents = new ArrayList<>();
    private Map<String, Statement.Block> debugMap = new HashMap<>();

    private TabPane pluginComponentPane = new TabPane();
    private StyleableVBox pluginSettingsPane = new StyleableVBox();
    private ScrollPane pluginSettingsScrollPane = new ScrollPane(pluginSettingsPane);
    private TreeNode<Button> commandsTree = new TreeNode<>(LanguageManager.get("label.commands"), Comparator.comparing(Labeled::getText));
    private TreeNode<Button> eventsTree = new TreeNode<>(LanguageManager.get("label.events"), Comparator.comparing(Labeled::getText));
    private TreeNode<Button> otherTree = new TreeNode<>(LanguageManager.get("label.other"), Comparator.comparing(Labeled::getText));
    private CheckBox debugModeCheckBox = new CheckBox(LanguageManager.get("check_box.debug_mode"));
    private TextField pluginNameField = new TextField();
    private TextField pluginVerField = new TextField();
    private TextField pluginAuthorField = new TextField();
    private TextField pluginDescField = new TextField();
    private TextField pluginPrefixField = new TextField();
    private TextField pluginWebsiteField = new TextField();
    private TextField pluginDependField = new TextField();
    private TextField pluginSoftDependField = new TextField();
    private TextField pluginLoadBeforeField = new TextField();
    private TextArea pluginPermsField = new TextArea();
    private ListSelectionView<VisualBukkitExtension> extensionView = new ListSelectionView<>();

    protected Project(Path dir) throws IOException {
        this.dir = dir;
        dataFile = dir.resolve("data.json");
        resourcesDir = dir.resolve("ResourceFiles");
        buildDir = dir.resolve("Build");
        Files.createDirectories(resourcesDir);
        Files.createDirectories(buildDir);

        Label title = new Label(LanguageManager.get("label.plugin_settings"));
        title.setUnderline(true);

        StyleableGridPane settingsGrid = new StyleableGridPane();
        settingsGrid.addColumn(0,
                new Label(LanguageManager.get("label.plugin_name")), new Label(LanguageManager.get("label.plugin_version")),
                new Label(LanguageManager.get("label.plugin_author")), new Label(LanguageManager.get("label.plugin_description")),
                new Label(LanguageManager.get("label.plugin_prefix")), new Label(LanguageManager.get("label.plugin_website")),
                new Label(LanguageManager.get("label.plugin_depend")), new Label(LanguageManager.get("label.plugin_soft_depend")),
                new Label(LanguageManager.get("label.plugin_load_before")), new Label(LanguageManager.get("label.plugin_permissions")));
        settingsGrid.addColumn(1, pluginNameField, pluginVerField, pluginAuthorField, pluginDescField, pluginPrefixField, pluginWebsiteField, pluginDependField, pluginSoftDependField, pluginLoadBeforeField, pluginPermsField);

        pluginPermsField.prefWidthProperty().bind(pluginNameField.widthProperty());
        pluginNameField.textProperty().addListener((o, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !StringUtils.isAlphanumeric(newValue)) {
                pluginNameField.setText(oldValue);
            }
        });

        TreeNode<TreeNode<Button>> pluginComponentsTree = new TreeNode<>(LanguageManager.get("label.plugin_components"));
        pluginComponentsTree.add(commandsTree);
        pluginComponentsTree.add(eventsTree);
        pluginComponentsTree.add(otherTree);
        pluginComponentsTree.toggle();

        extensionView.setSourceHeader(new Label(LanguageManager.get("label.installed_extensions")));
        extensionView.setTargetHeader(new Label(LanguageManager.get("label.enabled_extensions")));
        extensionView.getSourceItems().addAll(ExtensionManager.getExtensions());
        extensionView.getStyleClass().add("extension-view");
        Stage extensionStage = new Stage();
        VisualBukkitApp.getSettingsManager().bindStyle(extensionView);
        extensionStage.initOwner(VisualBukkitApp.getStage());
        extensionStage.initModality(Modality.APPLICATION_MODAL);
        extensionStage.setTitle("Extension Manager");
        extensionStage.setScene(new Scene(extensionView, 1000, 600));
        extensionStage.setOnHidden(e -> {
            for (VisualBukkitExtension extension : BlockRegistry.getActiveExtensions()) {
                extension.deactivate(this);
            }
            BlockRegistry.setExtensions(this);
        });

        StyleableGridPane buttonGrid = new StyleableGridPane();
        buttonGrid.addColumn(0,
                new IconButton("build", LanguageManager.get("button.build_plugin"), e -> {
                    VisualBukkitApp.getLogger().show();
                    debugMap.clear();
                    PluginBuilder.build(this, debugModeCheckBox.isSelected());
                }),
                new IconButton("jar", LanguageManager.get("button.output_directory"), e -> {
                    Path targetDir = buildDir.resolve("target");
                    if (Files.exists(targetDir)) {
                        VisualBukkitApp.openDirectory(targetDir);
                    } else {
                        NotificationManager.displayError(LanguageManager.get("error.cannot_open_build_dir.title"), LanguageManager.get("error.cannot_open_build_dir.content"));
                    }
                }),
                new IconButton("folder", LanguageManager.get("button.resource_directory"), e -> VisualBukkitApp.openDirectory(resourcesDir)),
                new IconButton("deploy", LanguageManager.get("button.deploy_plugin"), e -> PluginBuilder.deploy(this)),
                new IconButton("plug", LanguageManager.get("button.extensions"), e -> extensionStage.show()));
        buttonGrid.addColumn(1, debugModeCheckBox);

        pluginSettingsPane.getStyleClass().add("plugin-settings-pane");
        pluginSettingsPane.getChildren().addAll(title, pluginComponentsTree, settingsGrid, buttonGrid);
        pluginSettingsScrollPane.setFitToWidth(true);
        pluginSettingsScrollPane.setFitToHeight(true);
        pluginComponentPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        pluginComponentPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        pluginComponentPane.setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof PluginComponentSource) {
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        pluginComponentPane.setOnDragDropped(e -> {
            Object source = e.getGestureSource();
            if (source instanceof PluginComponentSource s) {
                createPluginComponent(s.getBlockDefinition().createBlock(), true);
                e.setDropCompleted(true);
                e.consume();
            }
        });

        data = new JSONObject();

        if (Files.exists(dataFile)) {
            try {
                data = new JSONObject(Files.readString(dataFile));
            } catch (JSONException ignored) {}
        }

        pluginNameField.setText(data.optString("plugin.name", ""));
        pluginVerField.setText(data.optString("plugin.version", ""));
        pluginAuthorField.setText(data.optString("plugin.author", ""));
        pluginDescField.setText(data.optString("plugin.description", ""));
        pluginPrefixField.setText(data.optString("plugin.prefix", ""));
        pluginWebsiteField.setText(data.optString("plugin.website", ""));
        pluginDependField.setText(data.optString("plugin.dependencies", ""));
        pluginSoftDependField.setText(data.optString("plugin.soft-dependencies", ""));
        pluginLoadBeforeField.setText(data.optString("plugin.load-before", ""));
        pluginPermsField.setText(data.optString("plugin.permissions", ""));
        debugModeCheckBox.setSelected(data.optBoolean("debug-build-mode"));

        JSONArray extensionArray = data.optJSONArray("extensions");
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

        BlockRegistry.setExtensions(this);

        JSONArray componentArray = data.optJSONArray("plugin-components");
        if (componentArray != null) {
            for (Object obj : componentArray) {
                if (obj instanceof JSONObject componentJson) {
                    PluginComponent component = BlockRegistry.getPluginComponent(componentJson.optString("="));
                    if (component != null) {
                        createPluginComponent(component.createBlock(componentJson), false);
                    }
                }
            }
        }

        JSONArray openPluginComponents = data.optJSONArray("open-plugin-components");
        if (openPluginComponents != null) {
            for (int i = 0; i < openPluginComponents.length(); i++) {
                pluginComponentPane.getTabs().add(pluginComponents.get(openPluginComponents.getInt(i)).getTab());
            }
        }

        int currentPluginComponent = data.optInt("current-plugin-component");
        if (currentPluginComponent >= 0 && currentPluginComponent < pluginComponentPane.getTabs().size()) {
            pluginComponentPane.getSelectionModel().select(currentPluginComponent);
        }
    }

    protected void open() throws IOException {
        VisualBukkitApp.getSidePane().getTabs().get(1).setContent(pluginSettingsScrollPane);
        VisualBukkitApp.getSplitPane().getItems().set(1, pluginComponentPane);
        VisualBukkitApp.getStage().setTitle("Visual Bukkit - " + getName());
        DiscordRPC.discordUpdatePresence(new DiscordRichPresence
                .Builder("Developing " + getName())
                .setStartTimestamps(System.currentTimeMillis())
                .build());
    }

    protected void close() throws IOException {
        for (VisualBukkitExtension extension : extensionView.getTargetItems()) {
            extension.deactivate(this);
        }
        save();
    }

    public void save() throws IOException {
        JSONObject json = new JSONObject();
        json.put("plugin.name", getPluginName());
        json.put("plugin.version", getPluginVersion());
        json.put("plugin.author", getPluginAuthor());
        json.put("plugin.description", getPluginDescription());
        json.put("plugin.prefix", getPluginPrefix());
        json.put("plugin.website", getPluginWebsite());
        json.put("plugin.dependencies", getPluginDependencies());
        json.put("plugin.soft-dependencies", getPluginSoftDependencies());
        json.put("plugin.load-before", getPluginLoadBefore());
        json.put("plugin.permissions", getPluginPermissions());
        json.put("debug-build-mode", debugModeCheckBox.isSelected());
        json.put("open-tab", pluginComponentPane.getSelectionModel().getSelectedIndex());
        json.put("current-plugin-component", pluginComponentPane.getSelectionModel().getSelectedIndex());

        for (VisualBukkitExtension extension : extensionView.getTargetItems()) {
            json.append("extensions", extension.getName());
            extension.save(this, json);
        }

        for (PluginComponent.Block block : pluginComponents) {
            json.append("plugin-components", block.serialize());
        }

        for (Tab tab : pluginComponentPane.getTabs()) {
            json.append("open-plugin-components", pluginComponents.indexOf(getPluginComponent(tab)));
        }

        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
        Files.writeString(dataFile, (data = json).toString(2));
    }

    public void createPluginComponent(PluginComponent.Block block, boolean open) {
        pluginComponents.add(block);
        TreeNode<Button> tree = getTree(block);
        tree.add(block.getOpenButton());
        block.getTab().textProperty().addListener((observable, oldValue, newValue) -> tree.sort());
        if (open) {
            openPluginComponent(block);
        }
    }

    public void openPluginComponent(PluginComponent.Block block) {
        if (!pluginComponentPane.getTabs().contains(block.getTab())) {
            pluginComponentPane.getTabs().add(0, block.getTab());
        }
        pluginComponentPane.getSelectionModel().select(block.getTab());
    }

    public UndoManager.RevertableAction deletePluginComponent(PluginComponent.Block block) {
        return new UndoManager.RevertableAction() {
            @Override
            public void run() {
                pluginComponents.remove(block);
                pluginComponentPane.getTabs().remove(block.getTab());
                getTree(block).remove(block.getOpenButton());
            }
            @Override
            public void revert() {
                pluginComponents.add(block);
                pluginComponentPane.getTabs().add(0, block.getTab());
                pluginComponentPane.getSelectionModel().selectFirst();
                getTree(block).add(block.getOpenButton());
            }
        };
    }

    public PluginComponent.Block getCurrentPluginComponent() {
        Tab currentTab = pluginComponentPane.getSelectionModel().getSelectedItem();
        return currentTab != null ? getPluginComponent(currentTab) : null;
    }

    private PluginComponent.Block getPluginComponent(Tab tab) {
        return ((PluginComponent.Block.Pane) tab.getContent()).getBlock();
    }

    private TreeNode<Button> getTree(PluginComponent.Block block) {
        return block instanceof CompCommand.Block ? commandsTree : block instanceof EventComponent.Block ? eventsTree : otherTree;
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
                    createPluginComponent(component.createBlock(json), true);
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
                    Files.writeString(outputDir.toPath().resolve(UUID.randomUUID() + ".json"), getPluginComponent(tab).serialize().toString(2));
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

    public JSONObject getData() {
        return data;
    }

    public TabPane getPluginComponentPane() {
        return pluginComponentPane;
    }

    public StyleableVBox getPluginSettingsPane() {
        return pluginSettingsPane;
    }

    public List<PluginComponent.Block> getPluginComponents() {
        return Collections.unmodifiableList(pluginComponents);
    }

    public List<VisualBukkitExtension> getExtensions() {
        return Collections.unmodifiableList(extensionView.getTargetItems());
    }

    public Map<String, Statement.Block> getDebugMap() {
        return debugMap;
    }

    public String getPluginName() {
        return pluginNameField.getText();
    }

    public String getPluginVersion() {
        return pluginVerField.getText();
    }

    public String getPluginAuthor() {
        return pluginAuthorField.getText();
    }

    public String getPluginDescription() {
        return pluginDescField.getText();
    }

    public String getPluginPrefix() {
        return pluginPrefixField.getText();
    }

    public String getPluginWebsite() {
        return pluginWebsiteField.getText();
    }

    public String getPluginDependencies() {
        return pluginDependField.getText();
    }

    public String getPluginSoftDependencies() {
        return pluginSoftDependField.getText();
    }

    public String getPluginLoadBefore() {
        return pluginLoadBeforeField.getText();
    }

    public String getPluginPermissions() {
        return pluginPermsField.getText();
    }
}
