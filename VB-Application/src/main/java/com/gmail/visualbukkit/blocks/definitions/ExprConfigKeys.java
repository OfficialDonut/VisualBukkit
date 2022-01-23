package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConfigKeys extends Expression {

    public ExprConfigKeys() {
        super("expr-config-keys", "Config Keys", "Config", "All the keys in a config");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LIST;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Config", ClassInfo.of("org.bukkit.configuration.ConfigurationSection")), new ExpressionParameter("Deep", ClassInfo.BOOLEAN)) {
            @Override
            public String toJava() {
                return "PluginMain.createList(" + arg(0) + ".getKeys(" + arg(1) + "))";
            }
        };
    }
}
