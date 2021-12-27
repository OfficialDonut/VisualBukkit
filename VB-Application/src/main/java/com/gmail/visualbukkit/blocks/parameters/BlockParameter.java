package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.ui.StyleableHBox;
import javafx.scene.Node;
import javafx.scene.control.Label;

public abstract class BlockParameter<T extends Node> extends StyleableHBox {

    protected String labelText;
    protected Label label;
    protected T control;

    public BlockParameter(String labelText, T control) {
        this.labelText = labelText;
        getChildren().addAll(label = new Label(), this.control = control);
    }

    public String getLabelText() {
        return labelText;
    }

    public Label getLabel() {
        return label;
    }

    public T getControl() {
        return control;
    }

    public abstract String toJava();

    public abstract Object serialize();

    public abstract void deserialize(Object obj);

    public void update() {}

    public void prepareBuild(BuildContext buildContext) {}
}
