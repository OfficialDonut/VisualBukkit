package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The square root of a number", "Returns: number"})
public class ExprSquareRoot extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("square root of", double.class);
    }

    @Override
    public String toJava() {
        return "Math.sqrt(" + arg(0) + ")";
    }
}
