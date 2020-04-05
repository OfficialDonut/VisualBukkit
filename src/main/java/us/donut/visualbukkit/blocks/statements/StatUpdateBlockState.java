package us.donut.visualbukkit.blocks.statements;

import org.bukkit.block.BlockState;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description("Updates the block represented by this state")
public class StatUpdateBlockState extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("update", BlockState.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".update();";
    }
}
