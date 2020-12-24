package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Description("Checks if a number is greater than another number")
public class ExprIsGreaterThan extends ExpressionBlock<Boolean> {

    public ExprIsGreaterThan() {
        init(double.class, " ", new BinaryChoiceParameter(">", ">="), " ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + arg(1) + arg(2) + ")";
    }
}