package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprThisPlugin extends Expression {

    public ExprThisPlugin() {
        super("expr-this-plugin");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.plugin.java.JavaPlugin");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance()";
            }
        };
    }
}
