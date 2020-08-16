package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.TreeType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Generates a tree at a location")
@Category(StatementCategory.WORLD)
public class StatGenerateTree extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("generate", TreeType.class, "tree at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().generateTree(" + arg(1) + "," + arg(0) + ");";
    }
}
