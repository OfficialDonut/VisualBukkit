package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description({"The data of a block", "Changers: set", "Returns: block data"})
public class ExprBlockData extends ChangeableExpressionBlock<BlockData> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("data of", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getBlockData()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setBlockData(" + delta + ");" : null;
    }
}
