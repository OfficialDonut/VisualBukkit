package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.components.InputParameter;

@Description("Arbitrary java code")
public class ExprJavaCode extends ExpressionBlock<String> {

    public ExprJavaCode() {
        init("java ", new InputParameter());
    }

    @Override
    public String toJava() {
        return arg(0);
    }
}
