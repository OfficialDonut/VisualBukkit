package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Makes a string all uppercase", "Returns: string"})
public class ExprUppercaseString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "in uppercase");
    }

    @Override
    public String toJava() {
        return arg(0) + ".toUpperCase()";
    }
}
