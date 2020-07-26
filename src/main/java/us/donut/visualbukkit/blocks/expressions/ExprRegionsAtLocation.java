package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;

import java.util.List;

@Description({"The regions at a location", "Returns: list of protected regions", "Requires: WorldGuard"})
public class ExprRegionsAtLocation extends ExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("regions at", Location.class);
    }

    @Override
    public String toJava() {
        BuildContext.addPluginModule(PluginModule.WORLDGUARD);
        return "WorldGuardHook.getRegions(" + arg(0) + ")";
    }
}
