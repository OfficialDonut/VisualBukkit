package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;

@Description("A trigonometric function (radians)")
public class ExprTrigFunction extends ExpressionBlock<Double> {

    public ExprTrigFunction() {
        init(new ChoiceParameter("cos", "sin", "tan", "acos", "asin", "atan"), " ", double.class);
    }

    @Override
    public String toJava() {
        return "Math." + arg(0) + "(" + arg(1) + ")";
    }
}