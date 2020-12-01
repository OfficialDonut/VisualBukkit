package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ExpressionParameter;

@Description("Checks if two booleans are both true")
public class ExprBooleanAnd extends ExpressionBlock<Boolean> {

    private ExpressionParameter boolean1 = new ExpressionParameter(boolean.class);
    private ExpressionParameter boolean2 = new ExpressionParameter(boolean.class);

    public ExprBooleanAnd() {
        init(boolean1, " and ", boolean2);
    }

    @Override
    public String toJava() {
        return "(" + boolean1.toJava() + "&&" + boolean2.toJava() + ")";
    }

    public ExpressionParameter getBoolean1() {
        return boolean1;
    }

    public ExpressionParameter getBoolean2() {
        return boolean2;
    }
}
