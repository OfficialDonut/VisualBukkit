package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConfigToString extends Expression {

    public ExprConfigToString() {
        super("expr-config-to-string", "Config To String", "Config", "The string representation of a config");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Config", ClassInfo.of("org.bukkit.configuration.ConfigurationSection"))) {
            @Override
            public String toJava() {
                return "((org.bukkit.configuration.file.FileConfiguration)" + arg(0) + ").saveToString()";
            }
        };
    }
}
