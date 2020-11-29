package com.gmail.visualbukkit.blocks.components;

public class BinaryChoiceParameter extends ChoiceParameter {

    public BinaryChoiceParameter(String first, String second) {
        super(first, second);
    }

    public boolean isFirst() {
        return getSelectionModel().getSelectedIndex() == 0;
    }

    public boolean isSecond() {
        return !isFirst();
    }
}
