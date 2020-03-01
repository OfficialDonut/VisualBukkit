package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Math")
@Description({"The product of two numbers", "Returns: number"})
public class ExprMultiplication extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, "*", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "*" + arg(1) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return double.class;
    }
}
