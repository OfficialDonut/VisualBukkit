package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-set-global-variable", name = "Set Global Variable", description = "Sets the value of a global variable")
public class StatSetGlobalVariable extends StatementBlock {

    public StatSetGlobalVariable() {
        addParameter("Variable", new InputParameter());
        addParameter("Value", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        String variable = ExprGlobalVariable.getVariable(arg(0, buildInfo));
        ExprGlobalVariable.declareVariable(buildInfo.getMainClass(), variable);
        return "PluginMain." + variable + " = " + arg(1, buildInfo) + ";";
    }
}
