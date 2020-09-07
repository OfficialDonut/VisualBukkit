package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Rounds up a number", "Returns: number"})
public class ExprCeilNumber extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("ceil", double.class);
    }

    @Override
    public String toJava() {
        return "((int) Math.ceil(" + arg(0) + "))";
    }
}
