package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The dot product of two vectors", "Returns: number"})
public class ExprDotProduct extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("dot product of", Vector.class, "and", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".dot(" + arg(1) + ")";
    }
}
