package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"False boolean", "Returns: boolean"})
public class ExprFalse extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("false");
    }

    @Override
    public String toJava() {
        return "false";
    }

    @Override
    public Class<?> getReturnType() {
        return boolean.class;
    }
}
