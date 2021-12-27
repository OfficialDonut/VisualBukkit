package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprIsConfigKeySet extends Expression {

    public ExprIsConfigKeySet() {
        super("expr-is-config-key-set", "Is Config Key Set", "Config", "Checks if a config has a value for a key");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Config", ClassInfo.of("org.bukkit.configuration.Configuration")), new ExpressionParameter("Key", ClassInfo.STRING)) {
            @Override
            public String toJava() {
                return arg(0) + ".isSet(" + arg(1) + ")";
            }
        };
    }
}
