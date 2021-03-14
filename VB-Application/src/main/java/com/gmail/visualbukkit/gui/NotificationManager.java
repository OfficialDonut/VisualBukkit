package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class NotificationManager {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:m:s");

    public static void log(String string) {
        System.out.println("[" + LocalTime.now().format(TIME_FORMATTER) + " INFO] " + string);
    }

    public static void displayMessage(String title, String message) {
        log(message);
        Notifications.create()
                .owner(VisualBukkitApp.getInstance().getPrimaryStage())
                .darkStyle()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showInformation();
    }

    public static void displayError(String title, String message) {
        System.out.println("[" + LocalTime.now().format(TIME_FORMATTER) + " WARN] " + message);
        Toolkit.getDefaultToolkit().beep();
        Notifications.create()
                .owner(VisualBukkitApp.getInstance().getPrimaryStage())
                .darkStyle()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showError();
    }

    public static void displayException(String message, Throwable e) {
        try {
            String stackTrace = ExceptionUtils.getStackTrace(e);
            System.out.println("[" + LocalTime.now().format(TIME_FORMATTER) + " SEVERE] " + message + '\n' + stackTrace);
            Toolkit.getDefaultToolkit().beep();
            Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);
            TextArea textArea = new TextArea(stackTrace);
            textArea.setEditable(false);
            alert.getDialogPane().setContent(new VBox(5, new Label(message), textArea));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait();
        } catch (Exception ex) {
            Platform.exit();
        }
    }
}
