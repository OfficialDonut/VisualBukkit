package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"The character at a position in a string", "Returns: string"})
public class ExprCharacterOfString extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("character at position", int.class, "in", String.class);
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(1) + ".charAt(" + arg(0) + "))";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
