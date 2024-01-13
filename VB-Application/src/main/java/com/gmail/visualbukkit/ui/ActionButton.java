package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class ActionButton extends Button {

    public ActionButton(String text, EventHandler<ActionEvent> action) {
        super(text);
        setOnAction(action);
    }

    public ActionButton(String text, Ikon ikon, EventHandler<ActionEvent> action) {
        this(text, action);
        setGraphic(new FontIcon(ikon));
    }
}
