package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class StatSetConfigValue extends Statement {

    public StatSetConfigValue() {
        super("stat-set-config-value", "Set Config Value", "Config", "Sets a value in a config");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Config", ClassInfo.of("org.bukkit.configuration.ConfigurationSection")), new ExpressionParameter("Key", ClassInfo.STRING), new ExpressionParameter("Value", ClassInfo.OBJECT)) {
            @Override
            public String toJava() {
                return arg(0) + ".set(" + arg(1) + "," + arg(2) + ");";
            }
        };
    }
}
