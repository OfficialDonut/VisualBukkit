package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Removes leading and trailing whitespace from a string", "Returns: string"})
public class ExprTrimmedString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "trimmed");
    }

    @Override
    public String toJava() {
        return arg(0) + ".trim()";
    }
}
