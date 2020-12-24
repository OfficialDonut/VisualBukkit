package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("Replaces all occurrences of a regex in a string with another string")
public class ExprRegexReplace extends ExpressionBlock<String> {

    public ExprRegexReplace() {
        init("regex replace all ", String.class, " in ", String.class, " with ", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".replaceAll(" + arg(0) + "," + arg(2) + ")";
    }
}