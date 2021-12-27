package com.gmail.visualbukkit.blocks.parameters;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;

public class InputParameter extends BlockParameter<TextField> {

    public InputParameter(String label) {
        super(label, new TextField());
        control.prefColumnCountProperty().bind(control.textProperty().length().add(1));
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(getParent(), e);
            e.consume();
        });
    }

    @Override
    public String toJava() {
        return control.getText();
    }

    @Override
    public Object serialize() {
        return control.getText();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String) {
            control.setText((String) obj);
        }
    }
}
