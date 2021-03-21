package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

public class CompPluginEnable extends PluginComponent {

    public CompPluginEnable() {
        super("comp-plugin-enable");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                MethodSource<JavaClassSource> enableMethod = buildContext.getMainClass().getMethod("onEnable");
                enableMethod.setBody(enableMethod.getBody() +
                        "try {" +
                        buildContext.getLocalVariableDeclarations() +
                        getChildJava() +
                        "} catch (Exception e) { e.printStackTrace(); }");
            }
        };
    }
}
