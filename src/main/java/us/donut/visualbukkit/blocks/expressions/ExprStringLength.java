package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The length of a string", "Returns: number"})
public class ExprStringLength extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("length of", String.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".length()";
    }
}
