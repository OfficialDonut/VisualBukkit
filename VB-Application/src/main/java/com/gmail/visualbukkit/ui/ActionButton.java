package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ActionButton extends Button {

    public ActionButton(String text, EventHandler<ActionEvent> action) {
        super(text);
        setOnAction(action);
    }
}
