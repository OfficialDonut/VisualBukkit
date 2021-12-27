package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

import java.util.HashMap;

public class ExprNewHashMap extends Expression {

    public ExprNewHashMap() {
        super("expr-new-map", "New HashMap", "HashMap", "Creates a new HashMap");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(HashMap.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "new HashMap()";
            }
        };
    }
}
