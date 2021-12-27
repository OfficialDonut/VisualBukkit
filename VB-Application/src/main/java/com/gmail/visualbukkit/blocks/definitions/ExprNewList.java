package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.StringJoiner;

public class ExprNewList extends VarArgsExpression {

    public ExprNewList() {
        super("expr-new-list", "New List", "List", "Creates a new list");
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
                    return "new ArrayList()";
                }
                StringJoiner joiner = new StringJoiner(",");
                for (BlockParameter<?> parameter : parameters) {
                    joiner.add(parameter.toJava());
                }
                return "new ArrayList(Arrays.asList(" + joiner + "))";
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
