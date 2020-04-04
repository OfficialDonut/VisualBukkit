package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Math")
@Description({"The remainder after dividing a number by another", "Returns: number"})
public class ExprModulo extends ExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, "%", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "%" + arg(1) + ")";
    }
}
