package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Removes an entity")
@Category(StatementCategory.WORLD)
public class StatRemoveEntity extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("remove", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".remove();";
    }
}
