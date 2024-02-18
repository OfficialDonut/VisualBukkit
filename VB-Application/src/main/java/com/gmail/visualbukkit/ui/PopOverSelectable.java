package com.gmail.visualbukkit.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;

public interface PopOverSelectable {

    String getPinID();

    default Node[] getDisplayNodes() {
        return new Node[]{new Label(toString())};
    }
}
