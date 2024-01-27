package com.gmail.visualbukkit.reflection;

import com.gmail.visualbukkit.ui.PopOverSelectable;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class MethodInfo implements Parameterizable, PopOverSelectable, Comparable<MethodInfo> {

    public abstract String getName();

    public abstract ClassInfo getReturnType();

    public abstract boolean isStatic();

    public String getSignature() {
        return String.format("%s(%s)", getName(), getParameterString(p -> p.getType().getName()));
    }

    @Override
    public String getPinID() {
        return getSignature();
    }

    @Override
    public Node[] getDisplayNodes() {
        if (getReturnType() == null) {
            return PopOverSelectable.super.getDisplayNodes();
        }
        Label nameLabel = new Label(getName() + "(" + getParameterString(p -> p.getType().getSimpleName()) + ")");
        Label returnTypeLabel = new Label("→ (" + getReturnType().getSimpleName() + ")");
        nameLabel.getStyleClass().add("method-name-label");
        returnTypeLabel.getStyleClass().add("return-type-label");
        return new Node[]{nameLabel, returnTypeLabel};
    }

    @Override
    public int compareTo(MethodInfo o) {
        int i = getName().compareTo(o.getName());
        return i == 0 ? getSignature().compareTo(o.getSignature()) : i;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MethodInfo other && getSignature().equals(other.getSignature());
    }

    @Override
    public int hashCode() {
        return getSignature().hashCode();
    }

    @Override
    public String toString() {
        return getReturnType() != null ?
                String.format("%s(%s) → %s", getName(), getParameterString(p -> p.getType().getSimpleName()), getReturnType().getSimpleName()) :
                String.format("%s(%s)", getName(), getParameterString(p -> p.getType().getSimpleName()));
    }
}
