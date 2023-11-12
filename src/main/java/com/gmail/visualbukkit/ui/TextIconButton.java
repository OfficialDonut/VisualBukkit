package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class TextIconButton extends Button {

    public TextIconButton(String text) {
        super(text);
        getStyleClass().add("text-icon-button");
    }

    public TextIconButton(String text, EventHandler<ActionEvent> action) {
        this(text);
        setOnAction(action);
    }
}
