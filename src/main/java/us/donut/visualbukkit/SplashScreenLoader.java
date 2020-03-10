package us.donut.visualbukkit;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;

public class SplashScreenLoader extends Preloader {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        BorderPane rootPane = new BorderPane();
        rootPane.getStylesheets().add("/style.css");
        try (InputStream inputStream = VisualBukkit.class.getResourceAsStream("/icon.png")) {
            Image icon = new Image(inputStream, 150, 150, true, true);
            VBox vBox = new VBox(15, new Label("Visual Bukkit"), new ImageView(icon));
            vBox.getStyleClass().add("splash-screen");
            rootPane.setCenter(vBox);
            primaryStage.setTitle("Visual Bukkit v" + SplashScreenLoader.class.getPackage().getSpecificationVersion());
            primaryStage.setScene(new Scene(rootPane, 600, 500));
            primaryStage.getIcons().add(icon);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        }
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification && ((ProgressNotification) info).getProgress() == 1) {
            primaryStage.close();
        }
    }
}
