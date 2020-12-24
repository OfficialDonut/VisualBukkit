package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Checks if a string equals another ignoring case")
public class ExprEqualsIgnoreCase extends ExpressionBlock<Boolean> {

    public ExprEqualsIgnoreCase() {
        init(String.class, " = ", String.class, " ignoring case");
    }

    @Override
    public String toJava() {
        return arg(0) + ".equalsIgnoreCase(" + arg(1) + ")";
    }
}