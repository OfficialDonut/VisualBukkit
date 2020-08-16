package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Multiplies all components of a vector by a scalar", "Returns: vector"})
public class ExprScaledVector extends ExpressionBlock<Vector> {

    @Override
    protected Syntax init() {
        return new Syntax(Vector.class, "*", double.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".multiply(" + arg(1) + ")";
    }
}
