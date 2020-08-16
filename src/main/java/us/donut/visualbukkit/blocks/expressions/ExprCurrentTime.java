package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The current time in milliseconds", "Returns: number"})
public class ExprCurrentTime extends ExpressionBlock<Long> {

    @Override
    protected Syntax init() {
        return new Syntax("current time millis");
    }

    @Override
    public String toJava() {
        return "System.currentTimeMillis()";
    }
}
