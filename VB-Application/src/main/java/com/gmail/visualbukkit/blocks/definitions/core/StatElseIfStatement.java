package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.project.BuildInfo;

@BlockDefinition(id = "stat-else-if-statement", name = "Else If Statement", description = "Checks if a condition is true and the condition was false for the previous 'If Statement' or 'Else If Statement'")
public class StatElseIfStatement extends StatIfStatement {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getParentStatementHolder().getPrevious(this) instanceof StatIfStatement)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "else " + super.generateJava(buildInfo);
    }
}
