package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description({"The state of a block", "Returns: block state"})
public class ExprBlockState extends ExpressionBlock<BlockState> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("state of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getState()";
    }
}
