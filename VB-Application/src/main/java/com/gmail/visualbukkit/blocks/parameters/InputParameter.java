package com.gmail.visualbukkit.blocks.parameters;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;

public class InputParameter extends TextField implements BlockParameter {

    public InputParameter() {
        prefColumnCountProperty().bind(textProperty().length().add(1));
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(getParent(), e);
            e.consume();
        });
    }

    @Override
    public String toJava() {
        return getText();
    }

    @Override
    public Object serialize() {
        return getText();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String) {
            setText((String) obj);
        }
    }
}
