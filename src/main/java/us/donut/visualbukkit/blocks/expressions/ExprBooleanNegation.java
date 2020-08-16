package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The \"opposite\" of a boolean", "Returns: boolean"})
public class ExprBooleanNegation extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("!", boolean.class);
    }

    @Override
    public String toJava() {
        return "!" + arg(0);
    }
}
