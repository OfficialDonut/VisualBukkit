package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Generates a random decimal between two numbers (inclusive)", "Returns: number"})
public class ExprRandomDecimal extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("random decimal between", double.class, "and", double.class);
    }

    @Override
    public String toJava() {
        return "java.util.concurrent.ThreadLocalRandom.current().nextDouble(" + arg(0) + "," + arg(1) + "+1)";
    }

    @Override
    public Class<?> getReturnType() {
        return double.class;
    }
}
