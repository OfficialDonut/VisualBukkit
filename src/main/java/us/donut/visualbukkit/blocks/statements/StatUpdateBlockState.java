package us.donut.visualbukkit.blocks.statements;

import org.bukkit.block.BlockState;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Updates the block represented by this state")
@Category(StatementCategory.WORLD)
public class StatUpdateBlockState extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("update", BlockState.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".update();";
    }
}
