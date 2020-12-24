package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Removes leading and trailing whitespace from a string")
public class ExprTrimString extends ExpressionBlock<String> {

    public ExprTrimString() {
        init(String.class, " trimmed");
    }

    @Override
    public String toJava() {
        return arg(0) + ".trim()";
    }
}