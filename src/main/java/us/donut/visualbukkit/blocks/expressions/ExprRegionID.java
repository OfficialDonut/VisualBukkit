package us.donut.visualbukkit.blocks.expressions;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

@Name("Region ID")
@Description({"The ID of a region", "Returns: string", "Requires: WorldGuard"})
public class ExprRegionID extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("ID of", ProtectedRegion.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.WORLDGUARD);
        return arg(0) + ".getId()";
    }
}
