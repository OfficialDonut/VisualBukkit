package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The length of a vector", "Returns: number"})
public class ExprVectorLength extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("length of", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".length()";
    }
}
