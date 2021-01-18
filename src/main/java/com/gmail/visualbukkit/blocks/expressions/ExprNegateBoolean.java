package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;

@Description("The negation (opposite) of a boolean")
public class ExprNegateBoolean extends ExpressionBlock<Boolean> {

    private ExpressionParameter booleanParameter = new ExpressionParameter(boolean.class);

    public ExprNegateBoolean() {
        init("!", booleanParameter);
    }

    @Override
    public String toJava() {
        return "!" + arg(0);
    }

    public ExpressionParameter getBoolean() {
        return booleanParameter;
    }
}
