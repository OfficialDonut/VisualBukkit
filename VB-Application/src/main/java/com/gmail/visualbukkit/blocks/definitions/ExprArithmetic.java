package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

public class ExprArithmetic extends VarArgsExpression {

    private static final String[] OPERATIONS = {"+", "-", "*", "/", "%"};

    public ExprArithmetic() {
        super("expr-arithmetic");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.DOUBLE;
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                String java = null;
                for (int i = 1; i < getParameters().size(); i += 2) {
                    java = "(" + (java == null ? arg(0) : java) + " " + arg(i) + " " + arg(i + 1) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                addParameterLine("Operation", new ChoiceParameter(OPERATIONS));
                addParameterLine("Number", new ExpressionParameter(ClassInfo.DOUBLE));
            }

            @Override
            protected void decreaseSize() {
                int i = getBody().getChildren().size();
                getBody().getChildren().remove(i - 2, i);
                getParameters().remove(getParameters().size() - 1);
                getParameters().remove(getParameters().size() - 1);
            }
        };

        block.addParameterLine("Number", new ExpressionParameter(ClassInfo.DOUBLE));
        block.addParameterLine("Operation", new ChoiceParameter(OPERATIONS));
        block.addParameterLine("Number", new ExpressionParameter(ClassInfo.DOUBLE));

        return block;
    }
}
