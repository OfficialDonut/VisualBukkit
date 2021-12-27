package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprTrigFunction extends Expression {

    public ExprTrigFunction() {
        super("expr-trig-function", "Trig Function", "Math", "A trigonometric function");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("Function", "cos", "sin", "tan", "acos", "asin", "atan"), new ExpressionParameter("Number", ClassInfo.DOUBLE)) {
            @Override
            public String toJava() {
                return "Math." + arg(0) + "(" + arg(1) + ")";
            }
        };
    }
}
