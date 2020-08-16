package us.donut.visualbukkit.blocks.statements;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Teleports an entity to a location")
@Category(StatementCategory.WORLD)
public class StatTeleport extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("teleport", Entity.class, "to", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".teleport(" + arg(1) + ");";
    }
}
