package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprPluginName extends Expression {

    public ExprPluginName() {
        super("expr-plugin-name", "Plugin Name", "Bukkit", "The name of the plugin");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().getDescription().getName()";
            }
        };
    }
}