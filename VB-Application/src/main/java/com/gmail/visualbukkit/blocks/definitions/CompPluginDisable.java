package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.project.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompPluginDisable extends PluginComponent {

    public CompPluginDisable() {
        super("comp-plugin-disable");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> disableMethod = buildContext.getMainClass().getMethod("onDisable");
                disableMethod.setBody(disableMethod.getBody() +
                        "try {" +
                        buildContext.getLocalVariableDeclarations() +
                        toJava() +
                        "} catch (Exception e) { e.printStackTrace(); }");
            }
        };
    }
}
