package com.gmail.visualbukkit.ui;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class StyleableHBox extends HBox {

    public StyleableHBox() {
        getStyleClass().add("hbox");
    }

    public StyleableHBox(double spacing) {
        super(spacing);
        getStyleClass().add("hbox");
    }

    public StyleableHBox(Node... children) {
        super(children);
        getStyleClass().add("hbox");
    }

    public StyleableHBox(double spacing, Node... children) {
        super(spacing, children);
        getStyleClass().add("hbox");
    }
}
