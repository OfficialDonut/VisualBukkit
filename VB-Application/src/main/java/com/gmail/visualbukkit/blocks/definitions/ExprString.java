package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class ExprString extends SimpleExpression {

    public ExprString() {
        super("expr-string", "String", "String", "A string that supports '&' color codes");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter("")) {
            @Override
            public String toJava() {
                String str = arg(0);
                return str.contains("&") ? "ChatColor.translateAlternateColorCodes('&'," + str + ")" : str;
            }
        };
    }
}
