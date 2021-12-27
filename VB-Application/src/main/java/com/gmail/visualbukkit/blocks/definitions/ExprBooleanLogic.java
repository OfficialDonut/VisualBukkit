package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.VarArgsExpression;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.Map;
import java.util.TreeMap;

public class ExprBooleanLogic extends VarArgsExpression {

    private static final Map<String, String> OPERATIONS = new TreeMap<>();

    static {
        OPERATIONS.put("AND", "&&");
        OPERATIONS.put("OR", "||");
        OPERATIONS.put("XOR", "^");
    }

    public ExprBooleanLogic() {
        super("expr-boolean-logic", "Boolean Logic", "Math", "Boolean operations (AND, OR, XOR)");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Boolean", ClassInfo.BOOLEAN), new ChoiceParameter("Operation", OPERATIONS.keySet()), new ExpressionParameter("Boolean", ClassInfo.BOOLEAN)) {
            @Override
            public String toJava() {
                String java = null;
                for (int i = 1; i < parameters.length; i += 2) {
                    java = "(" + (java == null ? arg(0) : java) + " " + OPERATIONS.get(arg(i)) + " " + arg(i + 1) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                push(new ChoiceParameter("Operation", OPERATIONS.keySet()));
                push(new ExpressionParameter("Boolean", ClassInfo.BOOLEAN));
            }

            @Override
            protected void decreaseSize() {
                pop();
                pop();
            }
        };
    }
}
