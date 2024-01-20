package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-set-local-variable", name = "Set Local Variable", description = "Sets the value of a local variable")
public class StatSetLocalVariable extends StatementBlock {

    private final InputParameter varParameter;
    private final ExpressionParameter valueParameter;

    public StatSetLocalVariable() {
        varParameter = new InputParameter();
        varParameter.getStyleClass().add("local-variable-field");
        addParameter("Var", varParameter);
        addParameter("Value", valueParameter = new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    public StatSetLocalVariable(String var, ExpressionBlock value) {
        this();
        varParameter.setText(var);
        valueParameter.setExpression(value);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        buildInfo.addLocalVariable(ExprLocalVariable.getVariable(arg(0, buildInfo)));
        return ExprLocalVariable.getVariable(arg(0, buildInfo)) + " = " + arg(1, buildInfo) + ";";
    }
}
