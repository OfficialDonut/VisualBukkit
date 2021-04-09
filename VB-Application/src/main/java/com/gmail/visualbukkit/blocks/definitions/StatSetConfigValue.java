package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSetConfigValue extends Statement {

    public StatSetConfigValue() {
        super("stat-set-config-value");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.configuration.ConfigurationSection")), new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return arg(0) + ".set(" + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
