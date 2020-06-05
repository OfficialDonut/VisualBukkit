package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Converts an object to a string", "Returns: string"})
public class ExprToString extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Object.class, "to string");
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(0) + ")";
    }
}
