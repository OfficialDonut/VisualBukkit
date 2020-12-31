package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

@Description("A string literal that can have escape sequences")
public class ExprRawString extends ExpressionBlock<String> {

    public ExprRawString() {
        InputParameter input = new InputParameter();
        input.setStyle("-fx-control-inner-background: pink;");
        init(input);
    }

    @Override
    public String toJava() {
        return "\"" + arg(0) + "\"";
    }
}
