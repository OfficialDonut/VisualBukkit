package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Replaces all occurrences of a string in a string with another string", "Returns: string"})
public class ExprStringReplace extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("replace all", String.class, "in", String.class, "with", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".replace(" + arg(0) + "," + arg(2) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
