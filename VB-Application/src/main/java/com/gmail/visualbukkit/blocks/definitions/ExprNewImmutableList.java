package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.StringJoiner;

public class ExprNewImmutableList extends VarArgsExpression {

    public ExprNewImmutableList() {
        super("expr-new-immutable-list", "New Immutable List", "List", "Creates a new list that cannot be modified");
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
                int size = parameters.length;
                if (size == 0) {
                    return "Collections.EMPTY_LIST";
                }
                StringJoiner joiner = new StringJoiner(",");
                for (BlockParameter<?> parameter : parameters) {
                    joiner.add(parameter.toJava());
                }
                return "((List) Arrays.asList(" + joiner + "))";
            }

            @Override
            protected void increaseSize() {
                push(new ExpressionParameter("Object", ClassInfo.OBJECT));
            }

            @Override
            protected void decreaseSize() {
                pop();
            }
        };
    }
}
