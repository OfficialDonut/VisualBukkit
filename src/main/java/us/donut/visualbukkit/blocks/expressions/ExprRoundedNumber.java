package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Rounds a number to the nearest integer", "Returns: number"})
public class ExprRoundedNumber extends ExpressionBlock<Long> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, "rounded");
    }

    @Override
    public String toJava() {
        return "Math.round(" + arg(0) + ")";
    }
}
