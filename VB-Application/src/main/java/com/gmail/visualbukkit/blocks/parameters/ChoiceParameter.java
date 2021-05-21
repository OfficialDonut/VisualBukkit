package com.gmail.visualbukkit.blocks.parameters;

import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Collection;
import java.util.List;

public class ChoiceParameter extends ComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        this(List.of(choices));
    }

    public ChoiceParameter(Collection<String> choices) {
        getItems().addAll(choices);
        getSelectionModel().selectFirst();
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(getParent(), e);
            e.consume();
        });
        addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getButton() != MouseButton.PRIMARY) {
                hide();
                e.consume();
            }
        });
    }

    @Override
    public String toJava() {
        return getValue();
    }

    @Override
    public Object serialize() {
        return getValue();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String && getItems().contains(obj)) {
            setValue((String) obj);
        }
    }
}
