package com.gmail.visualbukkit.blocks.expressions;

import java.util.List;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.structures.StructFunction;

@Description("The arguments of a function")
@SuppressWarnings("rawtypes")
public class ExprFunctionArguments extends ExpressionBlock<List> {

    public ExprFunctionArguments() {
        init("arguments");
    }

    @Override
    public void update() {
        super.update();
        validateStructure("Function arguments must be used in a function", StructFunction.class);
    }

    @Override
    public String toJava() {
        return "functionArgs";
    }
}