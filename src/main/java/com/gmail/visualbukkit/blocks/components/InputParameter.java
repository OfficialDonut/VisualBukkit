package com.gmail.visualbukkit.blocks.components;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import org.json.JSONObject;

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
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        obj.put("value", getText());
        return obj;
    }

    @Override
    public void deserialize(JSONObject obj) {
        String value = obj.optString("value");
        if (value != null) {
            setText(value);
        }
    }

    @Override
    public String toString() {
        return toJava();
    }
}
