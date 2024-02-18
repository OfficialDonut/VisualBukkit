package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@BlockDefinition(id = "comp-procedure", name = "Procedure")
public class CompProcedure extends PluginComponentBlock {

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        MethodSource<JavaClassSource> procedureMethod = buildInfo.getMainClass().getMethod("procedure", String.class, List.class);
        procedureMethod.setBody(
                "if (procedure.equalsIgnoreCase(\"" + getPluginComponent().getName() + "\")) {" +
                generateChildrenJava(buildInfo) +
                "return;" +
                "}" +
                procedureMethod.getBody());
    }
}
