package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@Description("Runs code when the plugin enables")
public class StructPluginEnable extends StructureBlock {

    public StructPluginEnable() {
        init("plugin enable");
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> enableMethod = context.getMainClass().getMethod("onEnable");
        enableMethod.setBody(enableMethod.getBody() +
                "try {" +
                getChildJava() +
                "} catch (Exception e) { e.printStackTrace(); }");
    }
}
