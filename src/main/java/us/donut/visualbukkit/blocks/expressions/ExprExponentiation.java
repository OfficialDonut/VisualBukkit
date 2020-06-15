package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Math")
@Description({"Raises a number to a power", "Returns: number"})
public class ExprExponentiation extends ExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, "^", double.class);
    }

    @Override
    public String toJava() {
        return "Math.pow(" + arg(0) + "," + arg(1) + ")";
    }
}
