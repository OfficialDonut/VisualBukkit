package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The world of an entity", "Returns: world"})
public class ExprWorldOfEntity extends ExpressionBlock<World> {

    @Override
    protected Syntax init() {
        return new Syntax("world of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWorld()";
    }
}
