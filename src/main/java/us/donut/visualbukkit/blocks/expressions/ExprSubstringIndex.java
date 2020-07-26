package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The index of a substring in a string", "Returns: number"})
public class ExprSubstringIndex extends ExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("index of", String.class, "in", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".indexOf(" + arg(0) + ")";
    }
}
