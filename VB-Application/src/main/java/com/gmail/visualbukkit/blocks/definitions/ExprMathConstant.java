package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.SimpleExpression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;

public class ExprMathConstant extends SimpleExpression {

    public ExprMathConstant() {
        super("expr-math-constant");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("pi", "e")) {
            @Override
            public String toJava() {
                return "Math." + arg(0).toUpperCase();
            }
        };
    }
}
