package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class ExprRawString extends Expression {

    public ExprRawString() {
        super("expr-raw-string", ClassInfo.STRING);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter()) {
            @Override
            public String toJava() {
                return "\"" + arg(0) + "\"";
            }
        };
    }
}
