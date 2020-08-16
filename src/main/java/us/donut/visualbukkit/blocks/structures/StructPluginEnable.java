package us.donut.visualbukkit.blocks.structures;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;

@Description("Runs code when the plugin enables")
public class StructPluginEnable extends StructureBlock {

    @Override
    protected Syntax init() {
        return new Syntax("on plugin enable");
    }

    @Override
    public void insertInto(JavaClassSource mainClass) {
        MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
        String childJava = getChildJava();
        enableMethod.setBody(enableMethod.getBody() +
                "try {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "} catch (Exception e) { e.printStackTrace(); }");
    }
}
