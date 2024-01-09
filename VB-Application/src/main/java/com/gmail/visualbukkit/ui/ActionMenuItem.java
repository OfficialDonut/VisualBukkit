package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class ActionMenuItem extends MenuItem {

    public ActionMenuItem(String text, EventHandler<ActionEvent> e) {
        super(text);
        setOnAction(e);
    }
}
