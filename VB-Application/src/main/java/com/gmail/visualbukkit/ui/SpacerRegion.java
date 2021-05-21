package com.gmail.visualbukkit.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class SpacerRegion extends HBox {

    public SpacerRegion() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
