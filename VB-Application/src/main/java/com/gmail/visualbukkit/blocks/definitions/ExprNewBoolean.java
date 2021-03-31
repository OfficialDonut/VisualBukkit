package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;

public class ExprNewBoolean extends Expression {

    public ExprNewBoolean() {
        super("expr-new-boolean", ClassInfo.BOOLEAN);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("true", "false")) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
