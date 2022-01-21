package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprPluginAuthors extends Expression {

    public ExprPluginAuthors() {
        super("expr-plugin-authors", "Plugin Authors", "Bukkit", "Authors of the plugin");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LIST;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.createList(PluginMain.getInstance().getDescription().getAuthors())";
            }
        };
    }
}
