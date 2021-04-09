package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;

public class ExprNewlineCharacter extends Expression {

    public ExprNewlineCharacter() {
        super("expr-newline-character", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                return "\"\\n\"";
            }
        };
    }
}
