package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class ExprNewString extends SimpleExpression {

    public ExprNewString() {
        super("expr-new-string");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter()) {
            @Override
            public String toJava() {
                String str = arg(0);
                return str.contains("&") ? "ChatColor.translateAlternateColorCodes('&'," + str + ")" : str;
            }
        };
    }
}
