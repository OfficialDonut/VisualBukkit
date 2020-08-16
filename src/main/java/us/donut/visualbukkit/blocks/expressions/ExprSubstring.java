package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A substring of a string", "Returns: string"})
public class ExprSubstring extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("substring of", String.class, "from", int.class, "to", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".substring(" + arg(1) + "," + arg(2) + ")";
    }
}
