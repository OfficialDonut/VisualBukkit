package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.plugin.PluginBuilder;
import com.gmail.visualbukkit.plugin.Project;
import com.gmail.visualbukkit.plugin.ProjectManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectView extends ScrollPane {

    private TextField pluginNameField = new TextField();
    private TextField pluginVerField = new TextField();
    private TextField pluginAuthorField = new TextField();
    private TextField pluginDescField = new TextField();
    private TextField pluginDependField = new TextField();
    private TextField pluginSoftDependField = new TextField();
    private TextArea pluginConfigTextArea = new TextArea();

    public ProjectView() {
        Button editConfigButton = new Button("Edit Config");
        editConfigButton.setOnAction(e -> openConfig());

        Button addCanvasButton = new Button("Add Canvas");
        addCanvasButton.setOnAction(e -> ProjectManager.getCurrentProject().promptAddCanvas());

        Button buildButton = new Button("Build Plugin");
        buildButton.setOnAction(e -> {
            try {
                PluginBuilder.build(ProjectManager.getCurrentProject());
            } catch (IOException ex) {
                NotificationManager.displayException("Failed to build project", ex);
            }
        });

        pluginConfigTextArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.TAB) {
                pluginConfigTextArea.insertText(pluginConfigTextArea.getCaretPosition(), "  ");
                e.consume();
            }
        });

        Label titleLabel = new Label("Plugin Information");
        titleLabel.setUnderline(true);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.addColumn(0, new Label("Name:"), new Label("Version:"), new Label("Author:"), new Label("Description:"), new Label("Dependencies:"), new Label("Soft Depend:"));
        gridPane.addColumn(1, pluginNameField, pluginVerField, pluginAuthorField, pluginDescField, pluginDependField, pluginSoftDependField);

        VBox content = new VBox(10, titleLabel, gridPane, editConfigButton, addCanvasButton, buildButton);
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
        pluginConfigTextArea.setText(project.getPluginConfig());
    }

    public void openConfig() {
        if (pluginConfigTextArea.getParent() != null) {
            pluginConfigTextArea.getScene().getWindow().hide();
        }
        Stage stage = new Stage();
        stage.initOwner(VisualBukkit.getInstance().getPrimaryStage());
        stage.setTitle("Plugin Config");
        ScrollPane scrollPane = new ScrollPane(pluginConfigTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane, 600, 600);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.show();
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

    public String getPluginConfig() {
        return pluginConfigTextArea.getText();
    }
}
