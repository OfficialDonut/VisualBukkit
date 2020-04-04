package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"The length of a string", "Returns: number"})
public class ExprStringLength extends ExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("length of", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".length()";
    }
}
