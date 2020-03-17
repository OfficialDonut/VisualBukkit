package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Makes a string all lowercase", "Returns: string"})
public class ExprLowercaseString extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "in lowercase");
    }

    @Override
    public String toJava() {
        return arg(0) + ".toLowerCase()";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
