package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

import java.util.HashMap;

public class ExprNewMap extends Expression {

    public ExprNewMap() {
        super("expr-new-map");
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
