package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The size of a list", "Returns: number"})
public class ExprListSize extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("size of", List.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".size()";
    }
}
