package com.gmail.visualbukkit.extensions;

import javafx.scene.layout.GridPane;

public abstract class VisualBukkitExtension implements Comparable<VisualBukkitExtension> {

    public abstract String getName();

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract String getDescription();

    public void setupInfoPane(GridPane gridPane) {}

    public void activate() {}

    public void deactivate() {}

    @Override
    public int compareTo(VisualBukkitExtension other) {
        return equals(other) ? 0 : getName().compareTo(other.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            return getName().equals(((VisualBukkitExtension) obj).getName());
        }
        return false;
    }

    @Override
    public final String toString() {
        return getName();
    }
}
