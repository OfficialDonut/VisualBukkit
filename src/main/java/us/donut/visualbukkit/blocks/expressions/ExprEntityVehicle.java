package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The vehicle of an entity", "Returns: entity"})
public class ExprEntityVehicle extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("vehicle of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getVehicle()";
    }
}
