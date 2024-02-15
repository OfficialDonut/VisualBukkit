package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.SizedExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;

@BlockDefinition(id = "expr-boolean-logic", name = "Boolean Logic", description = "Boolean operations (AND, OR, XOR)")
public class ExprBooleanLogic extends SizedExpressionBlock {

    private static final Map<String, String> operations = new TreeMap<>();

    static {
        operations.put("AND", "&&");
        operations.put("OR", "||");
        operations.put("XOR", "^");
    }

    public ExprBooleanLogic() {
        addParameter("Boolean", new ExpressionParameter(ClassInfo.of(boolean.class)));
        addParameter("Operation", new ChoiceParameter(operations.keySet()));
        addParameter("Boolean", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of(boolean.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        StringJoiner joiner = new StringJoiner(" ");
        for (BlockParameter parameter : parameters) {
            joiner.add(parameter instanceof ChoiceParameter c ? operations.get(c.getValue()) : parameter.generateJava(buildInfo));
        }
        return "(" + joiner + ")";
    }

    @Override
    protected void incrementSize() {
        addParameter("Operation", new ChoiceParameter(operations.keySet()));
        addParameter("Boolean", new ExpressionParameter(ClassInfo.of(boolean.class)));
    }

    @Override
    protected void decrementSize() {
        removeParameters(parameters.size() - 2);
    }
}
