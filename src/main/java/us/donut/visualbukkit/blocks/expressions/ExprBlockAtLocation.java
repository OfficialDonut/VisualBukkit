package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category({"Block", "Location"})
@Description({"The block at a location", "Returns: block"})
public class ExprBlockAtLocation extends ExpressionBlock<Block> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("block at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBlock()";
    }
}
