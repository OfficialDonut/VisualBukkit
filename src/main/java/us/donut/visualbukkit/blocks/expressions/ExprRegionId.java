package us.donut.visualbukkit.blocks.expressions;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Description({"The id of a region", "Returns: string", "Requires: WorldGuard"})
public class ExprRegionId extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("id of", ProtectedRegion.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.WORLDGUARD);
        return arg(0) + ".getId()";
    }
}
