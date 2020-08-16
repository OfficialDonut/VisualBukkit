package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Generates a random decimal between two numbers (inclusive)", "Returns: number"})
public class ExprRandomDecimal extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("random decimal between", double.class, "and", double.class);
    }

    @Override
    public String toJava() {
        return "java.util.concurrent.ThreadLocalRandom.current().nextDouble(" + arg(0) + "," + arg(1) + "+1)";
    }
}
