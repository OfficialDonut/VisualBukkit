package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.Map;

public class ExprMapKeys extends Expression {

    public ExprMapKeys() {
        super("expr-map-keys", ClassInfo.LIST);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of(Map.class))) {
            @Override
            public String toJava() {
                return "PluginMain.createList(" + arg(0) + ".keySet())";
            }
        };
    }
}
