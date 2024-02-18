package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.definitions.gui.CompGUIClickHandler;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

@BlockDefinition(id = "stat-function-return", name = "Function Return", description = "Returns a value (must be used in a 'Function' plugin component)")
public class StatFunctionReturn extends StatementBlock {

    public StatFunctionReturn() {
        addParameter("Value", new ExpressionParameter(ClassInfo.of(Object.class)));
    }

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompFunction.class);
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "if (true) return " + arg(0, buildInfo) + ";";
    }
}
