package us.donut.visualbukkit.blocks.expressions;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The id of a region", "Returns: string", "Requires: WorldGuard"})
public class ExprRegionId extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("id of", ProtectedRegion.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getId()";
    }
}
