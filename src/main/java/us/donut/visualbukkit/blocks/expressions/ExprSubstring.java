package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"A substring of a string", "Returns: string"})
public class ExprSubstring extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("substring of", String.class, "from", int.class, "to", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".substring(" + arg(1) + "," + arg(2) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
