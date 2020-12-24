package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;

@Description("The newline character")
public class ExprNewlineCharacter extends ExpressionBlock<String> {

    public ExprNewlineCharacter() {
        init("newline");
    }

    @Override
    public String toJava() {
        return "\"\\n\"";
    }
}