package com.gmail.visualbukkit.ui;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class StyleableVBox extends VBox {

    public StyleableVBox() {
        getStyleClass().add("vbox");
    }

    public StyleableVBox(double spacing) {
        super(spacing);
        getStyleClass().add("vbox");
    }

    public StyleableVBox(Node... children) {
        super(children);
        getStyleClass().add("vbox");
    }

    public StyleableVBox(double spacing, Node... children) {
        super(spacing, children);
        getStyleClass().add("vbox");
    }
}
