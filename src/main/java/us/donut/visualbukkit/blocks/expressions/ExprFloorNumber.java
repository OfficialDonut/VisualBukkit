package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Rounds down a number", "Returns: number"})
public class ExprFloorNumber extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("floor", double.class);
    }

    @Override
    public String toJava() {
        return "((int)" + arg(0) + ")";
    }
}
