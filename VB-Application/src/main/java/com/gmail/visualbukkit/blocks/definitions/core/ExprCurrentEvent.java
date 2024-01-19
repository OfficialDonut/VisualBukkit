package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "expr-current-event", name = "Current Event", description = "The current event in a 'Event Listener' plugin component")
public class ExprCurrentEvent extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getPluginComponentBlock() instanceof CompEventListener)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public ClassInfo getReturnType() {
        return getPluginComponentBlock() instanceof CompEventListener e ? e.getEvent() : null;
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "event";
    }
}
