package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Makes the length of a vector equal to 1", "Returns vector"})
public class ExprNormalizedVector extends ExpressionBlock<Vector> {

    @Override
    protected Syntax init() {
        return new Syntax(Vector.class, "normalized");
    }

    @Override
    public String toJava() {
        return arg(0) + ".normalize()";
    }
}
