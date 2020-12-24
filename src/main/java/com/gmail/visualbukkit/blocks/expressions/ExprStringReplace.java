package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Replaces all occurrences of a string in a string with another string")
public class ExprStringReplace extends ExpressionBlock<String> {

    public ExprStringReplace() {
        init("replace all ", String.class, " in ", String.class, " with ", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".replace(" + arg(0) + "," + arg(2) + ")";
    }
}