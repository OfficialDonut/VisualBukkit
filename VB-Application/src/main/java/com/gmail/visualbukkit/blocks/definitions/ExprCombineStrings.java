package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprCombineStrings extends VarArgsExpression {

    public ExprCombineStrings() {
        super("expr-combine-strings");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.STRING;
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                String java = arg(0);
                for (int i = 1; i < getParameters().size(); i ++) {
                    java = "(" + java + " + " + arg(i) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                addParameterLine("String", new ExpressionParameter(ClassInfo.STRING));
            }

            @Override
            protected void decreaseSize() {
                getBody().getChildren().remove(getBody().getChildren().size() - 1);
                getParameters().remove(getParameters().size() - 1);
            }
        };

        block.addParameterLine("String", new ExpressionParameter(ClassInfo.STRING));
        block.addParameterLine("String", new ExpressionParameter(ClassInfo.STRING));

        return block;
    }
}
