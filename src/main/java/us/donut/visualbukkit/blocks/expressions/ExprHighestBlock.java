package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The highest block at a location", "Returns: block"})
public class ExprHighestBlock extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("highest block at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld().getHighestBlockAt(" + arg(0) + ")";
    }
}
