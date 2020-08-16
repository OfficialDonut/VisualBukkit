package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if at least one of two booleans is true", "Returns: boolean"})
public class ExprBooleanOr extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax(boolean.class, "or", boolean.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "||" + arg(1) + ")";
    }
}
