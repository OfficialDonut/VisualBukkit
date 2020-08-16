package us.donut.visualbukkit.blocks.statements;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Creates a world")
@Category(StatementCategory.WORLD)
public class StatCreateWorld extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("create world with", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".createWorld();";
    }
}
