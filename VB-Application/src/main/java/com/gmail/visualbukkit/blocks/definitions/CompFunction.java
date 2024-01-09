package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.blocks.parameters.StringParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@BlockDefinition(uid = "comp-function", name = "Function")
public class CompFunction extends PluginComponentBlock {

    public CompFunction() {
        addParameter("Name", new StringParameter());
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        MethodSource<JavaClassSource> functionMethod = buildInfo.getMainClass().getMethod("function", String.class, List.class);
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(" + arg(0) + ")) {" +
                buildInfo.getLocalVariableDeclarations() +
                generateChildrenJava() +
                "return null;" +
                "}" +
                functionMethod.getBody());
    }
}
