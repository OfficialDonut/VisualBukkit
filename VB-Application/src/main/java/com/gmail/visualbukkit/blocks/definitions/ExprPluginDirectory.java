package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

import java.io.File;

public class ExprPluginDirectory extends Expression {

    public ExprPluginDirectory() {
        super("expr-plugin-directory");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(File.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "PluginMain.getInstance().getDataFolder()";
            }
        };
    }
}
