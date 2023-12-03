package com.gmail.visualbukkit.blocks.parameters;

import javafx.scene.control.ComboBox;

public class ChoiceParameter extends ComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        getItems().addAll(choices);
        getSelectionModel().selectFirst();
    }

    @Override
    public String generateJava() {
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
