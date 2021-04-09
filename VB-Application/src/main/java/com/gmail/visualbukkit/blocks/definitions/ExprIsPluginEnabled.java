package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprIsPluginEnabled extends Expression {

    public ExprIsPluginEnabled() {
        super("expr-is-plugin-enabled", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return "Bukkit.getPluginManager().isPluginEnabled(" + arg(0) + ")";
            }
        };
    }
}
