package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.project.BuildInfo;
import javafx.event.Event;
import javafx.scene.control.TextArea;
import javafx.scene.input.ContextMenuEvent;
import org.apache.commons.text.StringEscapeUtils;

public class MultilineStringParameter extends TextArea implements BlockParameter {

    public MultilineStringParameter() {
        setEditable(true);
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(getParent(), e);
            e.consume();
        });
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return '"' + StringEscapeUtils.escapeJava(getText()) + '"';
    }

    @Override
    public Object serialize() {
        return getText();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s) {
            setText(s);
        }
    }
}
