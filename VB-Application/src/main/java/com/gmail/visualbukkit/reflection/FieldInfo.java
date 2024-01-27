package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.ui.PopOverSelectable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class FieldInfo implements PopOverSelectable, Comparable<FieldInfo> {

    public abstract String getName();

    public abstract ClassInfo getType();

    public abstract boolean isStatic();

    @Override
    public String getPinID() {
        return getName();
    }

    @Override
    public Node[] getDisplayNodes() {
        Label nameLabel = new Label(getName());
        Label returnTypeLabel = new Label("→ (" + getType().getSimpleName() + ")");
        nameLabel.getStyleClass().add("field-name-label");
        returnTypeLabel.getStyleClass().add("return-type-label");
        return new Node[]{nameLabel, returnTypeLabel};
    }

    @Override
    public int compareTo(FieldInfo o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof FieldInfo other && getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }
}
