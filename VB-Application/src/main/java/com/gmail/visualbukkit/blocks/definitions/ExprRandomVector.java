package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprRandomVector extends Expression {

    public ExprRandomVector() {
        super("expr-random-vector", ClassInfo.of("org.bukkit.util.Vector"));
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "org.bukkit.util.Vector.getRandom()";
            }
        };
    }
}
