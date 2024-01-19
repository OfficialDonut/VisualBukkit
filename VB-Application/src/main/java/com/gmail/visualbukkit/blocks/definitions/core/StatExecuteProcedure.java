package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.PluginComponentParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "stat-execute-procedure", name = "Execute Procedure", description = "Executes the given procedure")
public class StatExecuteProcedure extends StatementBlock {

    public StatExecuteProcedure() {
        addParameter("Procedure", new PluginComponentParameter(CompProcedure.class));
        addParameter("Arguments", new ExpressionParameter(ClassInfo.of(List.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "PluginMain.procedure(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
