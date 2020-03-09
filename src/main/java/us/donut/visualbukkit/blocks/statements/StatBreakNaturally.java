package us.donut.visualbukkit.blocks.statements;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Block")
@Description("Breaks a block and spawns drops")
public class StatBreakNaturally extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("break", Block.class, "naturally");
    }

    @Override
    public String toJava() {
        return arg(0) + ".breakNaturally();";
    }
}
