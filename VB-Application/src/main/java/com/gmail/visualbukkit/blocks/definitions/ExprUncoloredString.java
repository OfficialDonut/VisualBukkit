package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprUncoloredString extends Expression {

    public ExprUncoloredString() {
        super("expr-uncolored-string", "Uncolored String", "String", "Strips all colors from a string");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("String", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "ChatColor.stripColor(" + arg(0) + ")";
            }
        };
    }
}
