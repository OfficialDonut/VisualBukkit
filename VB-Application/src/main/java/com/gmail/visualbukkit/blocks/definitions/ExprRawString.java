package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;

public class ExprRawString extends Expression {

    public ExprRawString() {
        super("expr-raw-string", "Raw String", "String", "A string in which escape sequences can be used");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new InputParameter("String")) {
            @Override
            public String toJava() {
                return "\"" + arg(0) + "\"";
            }
        };
    }
}
