package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import org.bukkit.plugin.java.JavaPlugin;

public class ExprThisPlugin extends Expression {

    public ExprThisPlugin() {
        super("expr-this-plugin", JavaPlugin.class);
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
