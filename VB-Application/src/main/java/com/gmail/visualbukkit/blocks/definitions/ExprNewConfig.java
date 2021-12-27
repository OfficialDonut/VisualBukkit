package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprNewConfig extends Expression {

    public ExprNewConfig() {
        super("expr-new-config", "New Config", "Config", "A new config");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.configuration.file.YamlConfiguration");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "new org.bukkit.configuration.file.YamlConfiguration()";
            }
        };
    }
}
