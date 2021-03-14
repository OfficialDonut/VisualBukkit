package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.parameters.BinaryChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatElseIfStatement extends Container {

    public StatElseIfStatement() {
        super("stat-else-if-statement");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(boolean.class), new BinaryChoiceParameter("false", "true")) {
            @Override
            public void update() {
                super.update();
                BlockDefinition<?> previous = getPrevious().getOwner().getDefinition();
                if (getPrevious() instanceof Container.ChildConnector || (!(previous instanceof StatIfStatement) && !(previous instanceof StatElseIfStatement))) {
                    setInvalid(VisualBukkitApp.getString("tooltip.invalid_else"));
                }
            }

            @Override
            public String toJava() {
                return arg(1).equals("false") ?
                        ("else if (" + arg(0) + ") {" + getChildJava() + "}") :
                        ("else if (!" + arg(0) + ") {" + getChildJava() + "}");
            }
        };
    }
}
