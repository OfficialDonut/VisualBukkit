package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IconButton extends Button {

    private final FontIcon icon = new FontIcon();

    public IconButton(Ikon ikon) {
        getStyleClass().add("icon-button");
        icon.setIconCode(ikon);
        setGraphic(icon);
    }

    public IconButton(Ikon ikon, EventHandler<ActionEvent> action) {
        this(ikon);
        setOnAction(action);
    }

    public FontIcon getIcon() {
        return icon;
    }
}
