package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;

public class ExprFunctionValue extends Expression {

    public ExprFunctionValue() {
        super("expr-function-value", ClassInfo.OBJECT);
    }

    @Override
    public Block createBlock() {
        return new Block(this, new StringLiteralParameter(), new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public String toJava() {
                return "PluginMain.function(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }
}
