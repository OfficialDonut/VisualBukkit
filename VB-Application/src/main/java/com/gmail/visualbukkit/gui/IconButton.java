package com.gmail.visualbukkit.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IconButton extends Button {

    private static Map<String, Image> imageCache = new HashMap<>();

    public IconButton(String iconName, String tooltipText, EventHandler<ActionEvent> clickAction) {
        getStyleClass().add("icon-button");
        setOnAction(clickAction);
        setGraphic(new ImageView(imageCache.computeIfAbsent("/images/" + iconName + ".png", k -> {
            try (InputStream inputStream = IconButton.class.getResourceAsStream(k)) {
                return new Image(inputStream);
            } catch (IOException e) {
                NotificationManager.displayException("Failed to load image", e);
                return null;
            }
        })));
        if (tooltipText != null) {
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(Duration.millis(500));
            setTooltip(tooltip);
        }
    }
}
