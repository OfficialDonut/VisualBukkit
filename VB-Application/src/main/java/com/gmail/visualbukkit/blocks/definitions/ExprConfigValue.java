package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConfigValue extends Expression {

    public ExprConfigValue() {
        super("expr-config-value");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.OBJECT;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.configuration.ConfigurationSection")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".get(" + arg(1) + ")";
            }
        };
    }
}
