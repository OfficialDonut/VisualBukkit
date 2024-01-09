package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ContainerBlock;

@BlockDefinition(uid = "stat-else-statement", name = "Else Statement")
public class StatElseStatement extends ContainerBlock {

    @Override
    public void updateState() {
        super.updateState();
        if (!(getParentStatementHolder().getPrevious(this) instanceof StatIfStatement)) {
            pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        }
    }

    @Override
    public String generateJava() {
        return "else {" + generateChildrenJava() + "}";
    }
}
