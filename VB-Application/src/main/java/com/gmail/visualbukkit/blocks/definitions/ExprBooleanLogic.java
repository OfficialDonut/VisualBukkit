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
        super("expr-boolean-logic");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.BOOLEAN;
    }

    @Override
    public Block createBlock() {
        Block block = new Block(this) {
            @Override
            public String toJava() {
                String java = null;
                for (int i = 1; i < getParameters().size(); i += 2) {
                    java = "(" + (java == null ? arg(0) : java) + " " + OPERATIONS.get(arg(i)) + " " + arg(i + 1) + ")";
                }
                return java;
            }

            @Override
            protected void increaseSize() {
                addParameterLine("Operation", new ChoiceParameter(OPERATIONS.keySet()));
                addParameterLine("Boolean", new ExpressionParameter(ClassInfo.BOOLEAN));
            }

            @Override
            protected void decreaseSize() {
                int i = getBody().getChildren().size();
                getBody().getChildren().remove(i - 2, i);
                getParameters().remove(getParameters().size() - 1);
                getParameters().remove(getParameters().size() - 1);
            }
        };

        block.addParameterLine("Boolean", new ExpressionParameter(ClassInfo.BOOLEAN));
        block.addParameterLine("Operation", new ChoiceParameter(OPERATIONS.keySet()));
        block.addParameterLine("Boolean", new ExpressionParameter(ClassInfo.BOOLEAN));

        return block;
    }
}
