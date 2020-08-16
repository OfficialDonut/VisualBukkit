package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Raises a number to a power", "Returns: number"})
public class ExprExponentiation extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, "^", double.class);
    }

    @Override
    public String toJava() {
        return "Math.pow(" + arg(0) + "," + arg(1) + ")";
    }
}
