package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.CompletableFuture;

public class BackgroundTaskExecutor {

    public static void executeAndWait(Runnable task) {
        Stage stage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(progressIndicator);
        scene.setFill(Color.TRANSPARENT);
        stage.initOwner(VisualBukkitApp.getPrimaryStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                task.run();
            } catch (Exception e) {
                Platform.runLater(() -> VisualBukkitApp.displayException(e));
            } finally {
                Platform.runLater(stage::close);
            }
        });

        stage.setOnShown(e -> {
            Stage primaryStage = VisualBukkitApp.getPrimaryStage();
            stage.setX(primaryStage.getX() + primaryStage.getWidth() / 2 - stage.getWidth() / 2);
            stage.setY(primaryStage.getY() + primaryStage.getHeight() / 2 - stage.getHeight() / 2);
            if (future.isDone()) {
                Platform.runLater(stage::close);
            }
        });

        stage.showAndWait();
    }
}
