package com.gmail.visualbukkit.blocks.parameters;

import javafx.scene.control.ComboBox;

import java.util.Collection;

public class ChoiceParameter extends BlockParameter<ComboBox<String>> {

    public ChoiceParameter(String label, String... choices) {
        super(label, new ComboBox<>());
        control.getItems().addAll(choices);
        control.getSelectionModel().selectFirst();
    }

    public ChoiceParameter(String label, Collection<String> choices) {
        super(label, new ComboBox<>());
        control.getItems().addAll(choices);
        control.getSelectionModel().selectFirst();
    }

    @Override
    public String toJava() {
        return control.getValue();
    }

    @Override
    public Object serialize() {
        return control.getValue();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String && control.getItems().contains(obj)) {
            control.setValue((String) obj);
        }
    }
}
