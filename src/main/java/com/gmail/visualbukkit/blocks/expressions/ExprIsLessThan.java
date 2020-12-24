package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Description("Checks if a number is less than another number")
public class ExprIsLessThan extends ExpressionBlock<Boolean> {

    public ExprIsLessThan() {
        init(double.class, " ", new BinaryChoiceParameter("<", "<="), " ", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + arg(1) + arg(2) + ")";
    }
}