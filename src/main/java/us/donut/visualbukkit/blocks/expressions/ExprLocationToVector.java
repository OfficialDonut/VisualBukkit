package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Constructs a vector from a location", "Returns: vector"})
public class ExprLocationToVector extends ExpressionBlock<Vector> {

    @Override
    protected Syntax init() {
        return new Syntax("vector from", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".toVector()";
    }
}
