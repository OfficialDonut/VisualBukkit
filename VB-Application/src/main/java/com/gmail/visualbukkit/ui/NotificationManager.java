package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.base.Throwables;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;

public class NotificationManager {

    public static void displayMessage(String title, String message) {
        Notifications.create()
                .owner(VisualBukkitApp.getStage())
                .darkStyle()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(5))
                .showInformation();
    }

    public static void displayError(String title, String message) {
        Notifications.create()
                .owner(VisualBukkitApp.getStage())
                .darkStyle()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(5))
                .showError();
    }

    public static void displayException(String message, Throwable e) {
        String stackTrace = Throwables.getStackTraceAsString((e));
        System.out.println(message + "\n" + stackTrace);
        Toolkit.getDefaultToolkit().beep();
        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);
        VisualBukkitApp.getSettingsManager().style(alert.getDialogPane());
        TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        alert.getDialogPane().setContent(new VBox(5, new Label(message), textArea));
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait();
    }
}
