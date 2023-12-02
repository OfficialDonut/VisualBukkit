package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(uid = "stat-set-local-variable", name = "Set Local Variable")
public class StatSetLocalVariable extends StatementBlock {

    public StatSetLocalVariable() {
        InputParameter parameter = new InputParameter();
        parameter.getStyleClass().add("local-variable-field");
        addParameter("Var", parameter);
        addParameter("Value", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        buildInfo.addLocalVariable(ExprLocalVariable.getVariable(arg(0)));
    }

    @Override
    public String generateJava() {
        return ExprLocalVariable.getVariable(arg(0)) + " = " + arg(1) + ";";
    }
}
