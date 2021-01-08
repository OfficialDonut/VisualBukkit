package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.plugin.PluginBuilder;
import com.gmail.visualbukkit.plugin.Project;
import com.gmail.visualbukkit.plugin.ProjectManager;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.apache.commons.lang.StringUtils;

public class ProjectView extends ScrollPane {

    private TextField pluginNameField = new TextField();
    private TextField pluginVerField = new TextField();
    private TextField pluginAuthorField = new TextField();
    private TextField pluginDescField = new TextField();
    private TextField pluginDependField = new TextField();
    private TextField pluginSoftDependField = new TextField();

    public ProjectView() {
        pluginNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!StringUtils.isAlphanumericSpace(newValue)) {
                pluginNameField.setText(oldValue);
            }
        });

        Button addCanvasButton = new Button("Add Canvas");
        Button resourceFilesButton = new Button("Resource Files");
        Button buildButton = new Button("Build Plugin");

        addCanvasButton.setOnAction(e -> ProjectManager.getCurrentProject().promptAddCanvas());
        resourceFilesButton.setOnAction(e -> ProjectManager.getCurrentProject().openResourceFolder());
        buildButton.setOnAction(e -> PluginBuilder.build(ProjectManager.getCurrentProject()));

        addCanvasButton.prefWidthProperty().bind(resourceFilesButton.widthProperty());
        buildButton.prefWidthProperty().bind(resourceFilesButton.widthProperty());

        Label titleLabel = new Label("Plugin Information");
        titleLabel.setUnderline(true);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.addColumn(0, new Label("Name:"), new Label("Version:"), new Label("Author:"), new Label("Description:"), new Label("Dependencies:"), new Label("Soft Depend:"));
        gridPane.addColumn(1, pluginNameField, pluginVerField, pluginAuthorField, pluginDescField, pluginDependField, pluginSoftDependField);

        VBox content = new VBox(10, titleLabel, gridPane, addCanvasButton, resourceFilesButton, buildButton);
        content.setPadding(new Insets(10));

        setContent(content);
        setFitToWidth(true);
        setFitToHeight(true);
    }

    public void show(Project project) {
        pluginNameField.setText(project.getPluginName());
        pluginVerField.setText(project.getPluginVersion());
        pluginAuthorField.setText(project.getPluginAuthor());
        pluginDescField.setText(project.getPluginDescription());
        pluginDependField.setText(project.getPluginDependencies());
        pluginSoftDependField.setText(project.getPluginSoftDepend());
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

    public String getPluginDepend() {
        return pluginDependField.getText();
    }

    public String getPluginSoftDepend() {
        return pluginSoftDependField.getText();
    }
}
