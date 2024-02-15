package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import java.util.List;

@BlockDefinition(id = "comp-function", name = "Function")
public class CompFunction extends PluginComponentBlock {

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        MethodSource<JavaClassSource> functionMethod = buildInfo.getMainClass().getMethod("function", String.class, List.class);
        functionMethod.setBody(
                "if (function.equalsIgnoreCase(\"" + getPluginComponent().getName() + "\")) {" +
                generateChildrenJava(buildInfo) +
                "return null;" +
                "}" +
                functionMethod.getBody());
    }
}
