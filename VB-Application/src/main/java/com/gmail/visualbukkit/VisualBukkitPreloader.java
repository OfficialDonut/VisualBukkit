package com.gmail.visualbukkit;

import com.google.common.base.Throwables;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class VisualBukkitPreloader extends Preloader {

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        try (InputStream inputStream = VisualBukkitApp.class.getResourceAsStream("/images/icon.png")) {
            Image icon = new Image(inputStream);
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(250);
            imageView.setFitHeight(250);
            Label label = new Label("Visual Bukkit");
            label.setFont(Font.loadFont(VisualBukkitPreloader.class.getResource("/fonts/JetBrainsMono-Bold.ttf").toExternalForm(), 48));
            label.setTextFill(Color.WHITE);
            VBox rootPane = new VBox(20, label, imageView);
            rootPane.setStyle("-fx-alignment: center; -fx-background-color: rgb(32, 34, 37);");
            stage.setScene(new Scene(rootPane, 600, 500));
            stage.getIcons().add(icon);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
        if (notification instanceof ProgressNotification && ((ProgressNotification) notification).getProgress() == 1) {
            stage.hide();
        }
    }

    @Override
    public boolean handleErrorNotification(ErrorNotification notification) {
        JOptionPane.showMessageDialog(null, Throwables.getStackTraceAsString(notification.getCause()), "Failed to launch Visual Bukkit", JOptionPane.ERROR_MESSAGE);
        return true;
    }
}
