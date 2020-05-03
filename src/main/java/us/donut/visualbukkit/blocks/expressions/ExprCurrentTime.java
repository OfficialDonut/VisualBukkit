package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The current time in milliseconds", "Returns: number"})
public class ExprCurrentTime extends ExpressionBlock<Long> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("current time millis");
    }

    @Override
    public String toJava() {
        return "System.currentTimeMillis()";
    }
}
