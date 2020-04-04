package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category({"Block", "Location"})
@Description({"The location of a block", "Returns: location"})
public class ExprBlockLocation extends ExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("location of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}
