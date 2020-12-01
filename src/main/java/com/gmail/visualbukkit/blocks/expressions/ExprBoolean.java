package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.BinaryChoiceParameter;

@Description("A boolean (true or false)")
public class ExprBoolean extends ExpressionBlock<Boolean> {

    public ExprBoolean() {
        init(new BinaryChoiceParameter("true", "false"));
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
