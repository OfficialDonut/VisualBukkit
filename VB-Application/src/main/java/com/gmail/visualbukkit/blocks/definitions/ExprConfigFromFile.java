package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprConfigFromFile extends Expression {

    public ExprConfigFromFile() {
        super("expr-config-from-file");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.configuration.file.YamlConfiguration");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("java.io.File"))) {
            @Override
            public String toJava() {
                return "org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(" + arg(0) + ")";
            }
        };
    }
}
