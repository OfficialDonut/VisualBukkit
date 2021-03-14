package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.Container;
import com.gmail.visualbukkit.blocks.Statement;

public class StatElseStatement extends Container {

    public StatElseStatement() {
        super("stat-else-statement");
    }

    @Override
    public Statement.Block createBlock() {
        return new Block(this) {
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
                return "else {" + getChildJava() + "}";
            }
        };
    }
}
