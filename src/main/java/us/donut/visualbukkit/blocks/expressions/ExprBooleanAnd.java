package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if two booleans are both true", "Returns: boolean"})
public class ExprBooleanAnd extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax(boolean.class, "and", boolean.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "&&" + arg(1) + ")";
    }
}
