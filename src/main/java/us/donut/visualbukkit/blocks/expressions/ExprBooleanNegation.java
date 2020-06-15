package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The \"opposite\" of a boolean", "Returns: boolean"})
public class ExprBooleanNegation extends ExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("!", boolean.class);
    }

    @Override
    public String toJava() {
        return "!" + arg(0);
    }
}
