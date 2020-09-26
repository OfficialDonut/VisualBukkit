package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The value of pi", "Returns: number"})
public class ExprPi extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("pi");
    }

    @Override
    public String toJava() {
        return "Math.PI";
    }
}
