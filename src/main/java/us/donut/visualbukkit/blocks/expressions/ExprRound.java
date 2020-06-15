package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Math")
@Description({"Rounds a number to the nearest integer", "Returns: number"})
public class ExprRound extends ExpressionBlock<Long> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, "rounded");
    }

    @Override
    public String toJava() {
        return "Math.round(" + arg(0) + ")";
    }
}
