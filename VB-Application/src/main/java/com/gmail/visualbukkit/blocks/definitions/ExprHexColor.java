package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprHexColor extends Expression {

    public ExprHexColor() {
        super("expr-hex-color", "Hex Color", "String", "A hex color code");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Hex", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "net.md_5.bungee.api.ChatColor.of(" + arg(0) + ").toString()";
            }
        };
    }
}
