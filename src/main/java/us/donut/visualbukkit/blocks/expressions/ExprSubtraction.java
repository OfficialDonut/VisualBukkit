package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The difference of two numbers", "Returns: number"})
public class ExprSubtraction extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, "-", double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "-" + arg(1) + ")";
    }
}
