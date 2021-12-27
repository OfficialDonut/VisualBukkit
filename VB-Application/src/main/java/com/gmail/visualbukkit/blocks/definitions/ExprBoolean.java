package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;

public class ExprBoolean extends SimpleExpression {

    public ExprBoolean() {
        super("expr-boolean", "Boolean", "Math", "A boolean (true or false)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("", "true", "false")) {
            @Override
            public String toJava() {
                return arg(0);
            }
        };
    }
}
