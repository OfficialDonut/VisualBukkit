package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprIPOfPlayer extends Expression {

    public ExprIPOfPlayer() {
        super("expr-ip-of-player");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.entity.Player"))) {
            @Override
            public String toJava() {
                return arg(0) + ".getAddress().getHostName()";
            }
        };
    }
}
