package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprPluginVersion extends Expression {

    public ExprPluginVersion() {
        super("expr-plugin-version", "Plugin Version", "Bukkit", "The version of the plugin");
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
                return "PluginMain.getInstance().getDescription().getVersion()";
            }
        };
    }
}
