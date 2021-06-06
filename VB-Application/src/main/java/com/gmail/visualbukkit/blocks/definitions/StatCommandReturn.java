package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatCommandReturn extends Statement {

    public StatCommandReturn() {
        super("stat-command-return");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.BOOLEAN)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-command");
            }

            @Override
            public String toJava() {
                return "if (true) return " + arg(0) + ";";
            }
        };
    }
}
