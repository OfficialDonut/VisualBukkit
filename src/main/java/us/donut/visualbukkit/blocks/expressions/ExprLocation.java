package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Location")
@Description({"A location in a world", "Returns: location"})
public class ExprLocation extends ExpressionBlock<Location> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("location at", double.class, ",", double.class, ",", double.class, "in", World.class);
    }

    @Override
    public String toJava() {
        return "new Location(" + arg(3) + "," + arg(0) + "," + arg(1) + "," + arg(2) + ")";
    }
}
