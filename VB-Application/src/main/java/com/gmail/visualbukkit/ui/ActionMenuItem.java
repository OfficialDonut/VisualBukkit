package com.gmail.visualbukkit.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class ActionMenuItem extends MenuItem {

    public ActionMenuItem(String text, EventHandler<ActionEvent> e) {
        super(text);
        setOnAction(e);
    }

    public ActionMenuItem(String text, Ikon ikon, EventHandler<ActionEvent> e) {
        this(text, e);
        setGraphic(new FontIcon(ikon));
    }
}
