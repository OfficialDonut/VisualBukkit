package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprPluginVersion extends Expression {

    public ExprPluginVersion() {
        super("expr-plugin-version", ClassInfo.STRING);
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
