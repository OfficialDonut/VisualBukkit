package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;

import java.util.List;

@Description("The value returned by a function")
public class ExprFunctionValue extends ExpressionBlock<Object> {

    public ExprFunctionValue() {
        init("function ", new StringLiteralParameter(), " with args ", List.class);
    }

    @Override
    public String toJava() {
        return "function(" + arg(0) + "," + arg(1) + ")";
    }
}