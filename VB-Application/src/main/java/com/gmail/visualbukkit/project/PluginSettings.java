package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.io.Resources;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PluginSettings {

    private final GridPane gridPane = new GridPane();
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

    public PluginSettings() {
        gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("label.plugin_settings_name")), pluginNameField);
        gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("label.plugin_settings_version")), pluginVersionField);
        gridPane.addRow(2, new Label(VisualBukkitApp.localizedText("label.plugin_settings_description")), pluginDescriptionField);
        gridPane.addRow(3, new Label(VisualBukkitApp.localizedText("label.plugin_settings_authors")), pluginAuthorsField);
        gridPane.addRow(4, new Label(VisualBukkitApp.localizedText("label.plugin_settings_prefix")), pluginPrefixField);
        gridPane.addRow(5, new Label(VisualBukkitApp.localizedText("label.plugin_settings_website")), pluginWebsiteField);
        gridPane.addRow(6, new Separator(), new Separator());
        gridPane.addRow(7, new Label(VisualBukkitApp.localizedText("label.plugin_settings_dependencies")), pluginDependenciesField);
        gridPane.addRow(8, new Label(VisualBukkitApp.localizedText("label.plugin_settings_softdepend")), pluginSoftDependField);
        gridPane.addRow(9, new Label(VisualBukkitApp.localizedText("label.plugin_settings_loadbefore")), pluginLoadBeforeField);
        gridPane.addRow(10, new Label(VisualBukkitApp.localizedText("label.plugin_settings_permissions")), pluginPermissionsField);
        gridPane.getStyleClass().add("plugin-settings-grid");
    }

    public String createPluginYml(String pluginName, String version, String mainClassName) throws IOException {
        String template = Resources.toString(PluginSettings.class.getResource("/plugin/plugin.yml"), StandardCharsets.UTF_8);
        StringBuilder builder = new StringBuilder(template.replace("{NAME}", pluginName).replace("{VERSION}", version).replace("{MAIN_CLASS}", mainClassName) + "\n");
        if (!getPluginAuthors().isBlank()) {
            builder.append("authors: [").append(getPluginAuthors()).append("]\n");
        }
        if (!getPluginDescription().isBlank()) {
            builder.append("description: \"").append(getPluginDescription()).append("\"\n");
        }
        if (!getPluginWebsite().isBlank()) {
            builder.append("website: \"").append(getPluginWebsite()).append("\"\n");
        }
        if (!getPluginDependencies().isBlank()) {
            builder.append("depend: [").append(getPluginDependencies()).append("]\n");
        }
        if (!getPluginSoftDepend().isBlank()) {
            builder.append("softdepend: [").append(getPluginSoftDepend()).append("]\n");
        }
        if (!getPluginLoadBefore().isBlank()) {
            builder.append("loadbefore: [").append(getPluginLoadBefore()).append("]\n");
        }
        if (!getPluginPrefix().isBlank()) {
            builder.append("prefix: \"").append(getPluginPrefix()).append("\"\n");
        }
        if (!getPluginPermissions().isBlank()) {
            builder.append("permissions:\n  ").append(getPluginPermissions().replace("\n", "\n  ")).append("\n");
        }
        return builder.toString();
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("name", pluginNameField.getText());
        json.put("version", pluginVersionField.getText());
        json.put("description", pluginDescriptionField.getText());
        json.put("authors", pluginAuthorsField.getText());
        json.put("prefix", pluginPrefixField.getText());
        json.put("website", pluginWebsiteField.getText());
        json.put("dependencies", pluginDependenciesField.getText());
        json.put("soft-depend", pluginSoftDependField.getText());
        json.put("load-before", pluginLoadBeforeField.getText());
        json.put("permissions", pluginPermissionsField.getText());
        return json;
    }

    public void deserialize(JSONObject json) {
        pluginNameField.setText(json.optString("name", ""));
        pluginVersionField.setText(json.optString("version", ""));
        pluginDescriptionField.setText(json.optString("description", ""));
        pluginAuthorsField.setText(json.optString("authors", ""));
        pluginPrefixField.setText(json.optString("prefix", ""));
        pluginWebsiteField.setText(json.optString("website", ""));
        pluginDependenciesField.setText(json.optString("dependencies", ""));
        pluginSoftDependField.setText(json.optString("soft-depend", ""));
        pluginLoadBeforeField.setText(json.optString("load-before", ""));
        pluginPermissionsField.setText(json.optString("permissions", ""));
    }

    public GridPane getGrid() {
        return gridPane;
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
