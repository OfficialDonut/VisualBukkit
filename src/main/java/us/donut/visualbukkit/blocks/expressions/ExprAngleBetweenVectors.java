package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The angle between two vectors in radians", "Returns: number"})
public class ExprAngleBetweenVectors extends ExpressionBlock<Float> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("angle between", Vector.class, "and", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".angle(" + arg(1) + ")";
    }
}
