package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Location")
@Description({"The distance between two locations", "Returns: number"})
public class ExprLocationDistance extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("distance between", Location.class, "and", Location.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".distance(" + arg(1) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return double.class;
    }
}
