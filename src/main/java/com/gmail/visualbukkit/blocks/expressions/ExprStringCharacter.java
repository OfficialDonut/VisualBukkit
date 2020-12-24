package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The character at a position in a string")
public class ExprStringCharacter extends ExpressionBlock<String> {

    public ExprStringCharacter() {
        init("character ", int.class, " of ", String.class);
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(1) + ".charAt(" + arg(0) + "))";
    }
}