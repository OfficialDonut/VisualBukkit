package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The regions in a world", "Returns: list of protected regions", "Requires: WorldGuard"})
public class ExprRegionsInWorld extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("regions in", Location.class);
    }

    @Override
    public String toJava() {
        return "WorldGuardHook.getRegions(" + arg(0) + ")";
    }
}
