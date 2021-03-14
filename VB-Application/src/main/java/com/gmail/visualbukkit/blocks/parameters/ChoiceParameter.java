package com.gmail.visualbukkit.blocks.parameters;

import javafx.event.Event;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.SearchableComboBox;

import java.util.Arrays;
import java.util.Collection;

public class ChoiceParameter extends SearchableComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        this(Arrays.asList(choices));
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
