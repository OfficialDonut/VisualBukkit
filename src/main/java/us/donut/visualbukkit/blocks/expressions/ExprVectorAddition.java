package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The sum of two vectors", "Returns: vector"})
public class ExprVectorAddition extends ExpressionBlock<Vector> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Vector.class, "+", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".add(" + arg(1) + ")";
    }
}
