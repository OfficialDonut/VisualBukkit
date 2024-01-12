package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "stat-execute-procedure", name = "Execute Procedure")
public class StatExecuteProcedure extends StatementBlock {

    public StatExecuteProcedure() {
        addParameter("Procedure", new StringParameter());
        addParameter("Arguments", new ExpressionParameter(ClassInfo.of(List.class)));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "PluginMain.procedure(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + ");";
    }
}
