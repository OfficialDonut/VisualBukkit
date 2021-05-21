package com.gmail.visualbukkit.ui;

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

        setGraphic(new ImageView(imageCache.computeIfAbsent("/images/" + iconName + ".png", k -> {
            try (InputStream inputStream = IconButton.class.getResourceAsStream(k)) {
                return new Image(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        })));

        if (clickAction != null) {
            setOnAction(clickAction);
        }

        if (tooltipText != null) {
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setShowDelay(Duration.millis(500));
            setTooltip(tooltip);
        }
    }
}
