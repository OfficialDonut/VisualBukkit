package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.controlsfx.control.Notifications;

import java.awt.*;

public class NotificationManager {

    public static void displayMessage(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showInformation();
    }

    public static void displayError(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .hideAfter(Duration.seconds(4))
                .showError();
        Toolkit.getDefaultToolkit().beep();
    }

    public static void displayException(String message, Throwable e) {
        VisualBukkit.getLogger().severe(ExceptionUtils.getStackTrace(e));
        Alert alert = new Alert(Alert.AlertType.ERROR, null, ButtonType.CLOSE);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        VBox content = new VBox(5, new Text(message), new TextArea(ExceptionUtils.getStackTrace(e)));
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }
}
