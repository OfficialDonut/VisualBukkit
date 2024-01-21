package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;
import javafx.scene.control.ComboBox;

import java.util.Collection;

public class ChoiceParameter extends ComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        getItems().addAll(choices);
        getSelectionModel().selectFirst();
    }

    public ChoiceParameter(Collection<String> choices) {
        getItems().addAll(choices);
        getSelectionModel().selectFirst();
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return getValue();
    }

    @Override
    public Object serialize() {
        return getValue();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s && getItems().contains(s)) {
            setValue(s);
        }
    }
}
