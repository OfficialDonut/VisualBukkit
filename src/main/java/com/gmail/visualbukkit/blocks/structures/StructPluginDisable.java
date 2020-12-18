package com.gmail.visualbukkit.blocks.structures;

import com.gmail.visualbukkit.blocks.StructureBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;

@Description("Runs code when the plugin disables")
public class StructPluginDisable extends StructureBlock {

    public StructPluginDisable() {
        init("plugin disable");
    }

    @Override
    public void prepareBuild(BuildContext context) {
        MethodSource<JavaClassSource> disableMethod = context.getMainClass().getMethod("onDisable");
        disableMethod.setBody(disableMethod.getBody() +
                "try {" +
                getChildJava() +
                "} catch (Exception e) { e.printStackTrace(); }");
    }
}
