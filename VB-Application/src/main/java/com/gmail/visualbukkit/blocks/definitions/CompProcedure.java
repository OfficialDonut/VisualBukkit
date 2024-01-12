package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@BlockDefinition(id = "comp-procedure", name = "Procedure")
public class CompProcedure extends PluginComponentBlock {

    public CompProcedure() {
        addParameter("Name", new StringParameter());
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        MethodSource<JavaClassSource> procedureMethod = buildInfo.getMainClass().getMethod("procedure", String.class, List.class);
        procedureMethod.setBody(
                "if (procedure.equalsIgnoreCase(" + arg(0, buildInfo) + ")) {" +
                generateChildrenJava(buildInfo) +
                "return;" +
                "}" +
                procedureMethod.getBody());
    }
}
