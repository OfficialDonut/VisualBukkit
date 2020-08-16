package us.donut.visualbukkit.blocks.structures;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Runs code when the plugin disables")
public class StructPluginDisable extends StructureBlock {

    @Override
    protected Syntax init() {
        return new Syntax("on plugin disable");
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        MethodSource<JavaClassSource> disableMethod = mainClass.getMethod("onDisable");
        String childJava = getChildJava();
        disableMethod.setBody(disableMethod.getBody() +
                "try {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "} catch (Exception e) { e.printStackTrace(); }");
    }
}
