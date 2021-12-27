package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprColoredString extends Expression {

    public ExprColoredString() {
        super("expr-colored-string", "Colored String", "String", "Colors a string with '&' as the color code character");
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
                return "ChatColor.translateAlternateColorCodes('&'," + arg(0) + ")";
            }
        };
    }
}
