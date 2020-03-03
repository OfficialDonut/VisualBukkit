package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Combine Strings")
@Description({"Combines two strings together", "Returns: string"})
public class ExprStringConcatenation extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, "+", String.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "+" + arg(1) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
