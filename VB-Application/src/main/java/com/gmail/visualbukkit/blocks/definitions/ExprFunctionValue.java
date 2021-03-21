package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

import java.util.List;

public class ExprFunctionValue extends Expression {

    public ExprFunctionValue() {
        super("expr-function-value", Object.class);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter(), new ExpressionParameter(List.class)) {
            @Override
            public String toJava() {
                return "PluginMain.function(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
