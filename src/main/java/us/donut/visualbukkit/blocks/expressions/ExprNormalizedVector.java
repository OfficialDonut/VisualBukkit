package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Makes the length of a vector equal to 1", "Returns vector"})
public class ExprNormalizedVector extends ExpressionBlock<Vector> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Vector.class, "normalized");
    }

    @Override
    public String toJava() {
        return arg(0) + ".normalize()";
    }
}
