package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConfigKeys extends Expression {

    public ExprConfigKeys() {
        super("expr-config-keys", ClassInfo.LIST);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.configuration.ConfigurationSection"))) {
            @Override
            public String toJava() {
                return "PluginMain.createList(" + arg(0) + ".getKeys(false))";
            }
        };
    }
}
