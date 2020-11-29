package com.gmail.visualbukkit.util;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class CenteredHBox extends HBox {

    public CenteredHBox() {
        setAlignment(Pos.CENTER_LEFT);
    }

    public CenteredHBox(double spacing) {
        super(spacing);
        setAlignment(Pos.CENTER_LEFT);
    }

    public CenteredHBox(double spacing, Node... children) {
        super(spacing, children);
        setAlignment(Pos.CENTER_LEFT);
    }

    public CenteredHBox(Node... children) {
        super(children);
        setAlignment(Pos.CENTER_LEFT);
    }
}
