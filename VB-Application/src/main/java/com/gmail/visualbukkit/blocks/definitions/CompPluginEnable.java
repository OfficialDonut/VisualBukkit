package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@BlockDefinition(uid = "comp-plugin-enable", name = "Plugin Enable")
public class CompPluginEnable extends PluginComponentBlock {

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        MethodSource<JavaClassSource> enableMethod = buildInfo.getMainClass().getMethod("onEnable");
        enableMethod.setBody(enableMethod.getBody() +
                "try {" +
                buildInfo.getLocalVariableDeclarations() +
                generateChildrenJava() +
                "} catch (Exception e) { e.printStackTrace(); }");
    }
}
