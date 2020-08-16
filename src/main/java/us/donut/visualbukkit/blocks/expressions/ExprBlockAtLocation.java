package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The block at a location", "Returns: block"})
public class ExprBlockAtLocation extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("block at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBlock()";
    }
}
