package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The distance between two locations", "Returns: number"})
public class ExprLocationDistance extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("distance between", Location.class, "and", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".distance(" + arg(1) + ")";
    }
}
