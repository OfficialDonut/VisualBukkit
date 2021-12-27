package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCommandArgument extends Expression {

    public ExprCommandArgument() {
        super("expr-command-argument", "Command Argument", "Bukkit", "An argument of a command (indices start at 0)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Index", ClassInfo.INT)) {
            @Override
            public void update() {
                super.update();
                checkForPluginComponent(CompCommand.class);
            }

            @Override
            public String toJava() {
                return "(commandArgs.length > " + arg(0) + " ? commandArgs[" + arg(0) + "] : null" + ")";
            }
        };
    }
}
