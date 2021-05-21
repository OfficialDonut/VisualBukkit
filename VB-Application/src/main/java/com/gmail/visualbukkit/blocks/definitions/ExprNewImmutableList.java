package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.StringJoiner;

public class ExprNewImmutableList extends VarArgsExpression {

    public ExprNewImmutableList() {
        super("expr-new-immutable-list");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.LIST;
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public String toJava() {
                int size = getParameters().size();
                if (size == 0) {
                    return "Collections.EMPTY_LIST";
                }
                StringJoiner joiner = new StringJoiner(",");
                for (BlockParameter parameter : getParameters()) {
                    joiner.add(parameter.toJava());
                }
                return "((List) Arrays.asList(" + joiner + "))";
            }

            @Override
            protected void increaseSize() {
                addParameterLine("Object", new ExpressionParameter(ClassInfo.OBJECT));
            }

            @Override
            protected void decreaseSize() {
                getBody().getChildren().remove(getBody().getChildren().size() - 1);
                getParameters().remove(getParameters().size() - 1);
            }
        };
    }
}
