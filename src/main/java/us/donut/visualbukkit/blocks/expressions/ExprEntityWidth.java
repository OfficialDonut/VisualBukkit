package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The width of an entity", "Returns: number"})
public class ExprEntityWidth extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("width of", Entity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getWidth()";
    }
}
