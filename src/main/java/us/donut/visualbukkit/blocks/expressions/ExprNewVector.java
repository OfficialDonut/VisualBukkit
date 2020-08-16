package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A vector", "Returns: vector"})
public class ExprNewVector extends ExpressionBlock<Vector> {

    @Override
    protected Syntax init() {
        return new Syntax("vector(", double.class, ",", double.class, ",", double.class, ")");
    }

    @Override
    public String toJava() {
        return "new org.bukkit.util.Vector(" + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }
}
