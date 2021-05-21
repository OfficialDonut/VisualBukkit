package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprIsCommandSenderAPlayer extends Expression {

    public ExprIsCommandSenderAPlayer() {
        super("expr-is-command-sender-a-player");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent("comp-command");
            }

            @Override
            public String toJava() {
                return "(commandSender instanceof org.bukkit.entity.Player)";
            }
        };
    }
}
