package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The location of a block", "Returns: location"})
public class ExprBlockLocation extends ExpressionBlock<Location> {

    @Override
    protected Syntax init() {
        return new Syntax("location of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}
