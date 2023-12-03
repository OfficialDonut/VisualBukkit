package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(uid = "expr-current-event", name = "Current Event")
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
    public String generateJava() {
        return "event";
    }
}
