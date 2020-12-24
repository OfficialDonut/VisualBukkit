package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructFunction;

@Description("An argument of a function")
public class ExprFunctionArgument extends ExpressionBlock<Object> {

    public ExprFunctionArgument() {
        init("argument ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Function argument must be used in a function", StructFunction.class);
    }

    @Override
    public String toJava() {
        return "args.get(" + arg(0) + ")";
    }
}