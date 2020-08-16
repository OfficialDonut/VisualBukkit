package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Gets a location from a vector", "Returns: location"})
public class ExprVectorToLocation extends ExpressionBlock<Location> {

    @Override
    protected Syntax init() {
        return new Syntax("location from", Vector.class, "in", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".toLocation(" + arg(1) + ")";
    }
}
