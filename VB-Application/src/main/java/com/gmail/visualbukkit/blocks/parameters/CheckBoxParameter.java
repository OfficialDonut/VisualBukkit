package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;
import javafx.scene.control.CheckBox;

public class CheckBoxParameter extends CheckBox implements BlockParameter {

    public CheckBoxParameter(String text) {
        super(text);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return String.valueOf(isSelected());
    }

    @Override
    public Object serialize() {
        return isSelected();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof Boolean b) {
            setSelected(b);
        }
    }
}
