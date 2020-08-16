package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Spawns a new entity", "Returns: entity"})
public class ExprNewEntity extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("new", EntityType.class, "at", Location.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getWorld().spawnEntity(" + arg(1) + "," + arg(0) + ")";
    }
}
