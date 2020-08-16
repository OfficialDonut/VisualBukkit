package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The cross product of two vectors", "Returns: vector"})
public class ExprCrossProduct extends ExpressionBlock<Vector> {

    @Override
    protected Syntax init() {
        return new Syntax("cross product of", Vector.class, "and", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".crossProduct(" + arg(1) + ")";
    }
}
