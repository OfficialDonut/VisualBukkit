package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IconButton extends Button {

    private static Map<String, Image> imageCache = new HashMap<>();

    public IconButton(String iconName, String label, EventHandler<ActionEvent> clickAction) {
        super(label);
        setOnAction(clickAction);
        setGraphic(new ImageView(imageCache.computeIfAbsent("/images/" + iconName + ".png", k -> {
            try (InputStream inputStream = IconButton.class.getResourceAsStream(k)) {
                return new Image(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        })));
    }
}
