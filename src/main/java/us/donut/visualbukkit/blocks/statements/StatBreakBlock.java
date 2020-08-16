package us.donut.visualbukkit.blocks.statements;

import org.bukkit.block.Block;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Breaks a block and spawns drops")
@Category(StatementCategory.WORLD)
public class StatBreakBlock extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("naturally break", Block.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".breakNaturally();";
    }
}
