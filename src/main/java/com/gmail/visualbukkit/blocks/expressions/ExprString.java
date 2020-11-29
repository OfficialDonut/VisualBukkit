package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.StringLiteralParameter;

@Description("A string literal")
public class ExprString extends ExpressionBlock<String> {

    private StringLiteralParameter literal = new StringLiteralParameter();

    public ExprString() {
        init(literal);
    }

    @Override
    public String toJava() {
        return literal.toJava();
    }
}
