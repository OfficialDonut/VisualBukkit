package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Math")
@Description({"The value of pi", "Returns: number"})
public class ExprPi extends ExpressionBlock<Number> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("pi");
    }

    @Override
    public String toJava() {
        return "Math.PI";
    }
}
