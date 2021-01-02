package com.gmail.visualbukkit.extensions;

import javafx.scene.layout.GridPane;

public abstract class VisualBukkitExtension {

    public void setupManager(GridPane gridPane) {}

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract String getDescription();

    @Override
    public final String toString() {
        return getName();
    }
}
