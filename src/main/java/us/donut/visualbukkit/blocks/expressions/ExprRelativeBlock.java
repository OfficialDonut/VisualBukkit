package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Gets an adjacent block to a block", "Returns: block"})
public class ExprRelativeBlock extends ExpressionBlock<Block> {

    @Override
    protected Syntax init() {
        return new Syntax("block", BlockFace.class, "of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getRelative(" + arg(0) + ")";
    }
}
