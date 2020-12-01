package com.gmail.visualbukkit.blocks.components;

import javafx.event.Event;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.SearchableComboBox;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

public class ChoiceParameter extends SearchableComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        this(Arrays.asList(choices));
    }

    public ChoiceParameter(Collection<String> choices) {
        getItems().addAll(choices);
        setValue(getItems().get(0));
        setOnAction(e -> {
            if (getValue() == null) {
                setValue(getItems().get(0));
            }
        });
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
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        obj.put("value", getValue());
        return obj;
    }

    @Override
    public void deserialize(JSONObject obj) {
        String value = obj.optString("value");
        if (value != null) {
            setValue(value);
        }
    }
}
