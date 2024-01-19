package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;
import com.gmail.visualbukkit.project.BuildInfo;

@BlockDefinition(id = "stat-else-statement", name = "Else Statement", description = "Checks if the condition was false for the previous 'If Statement' or 'Else If Statement'")
public class StatElseStatement extends ContainerBlock {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getParentStatementHolder().getPrevious(this) instanceof StatIfStatement)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "else {" + generateChildrenJava(buildInfo) + "}";
    }
}
