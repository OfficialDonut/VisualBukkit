package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Replaces all occurrences of a string in a string with another string", "Returns: string"})
public class ExprStringReplace extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("replace all", String.class, "in", String.class, "with", String.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".replace(" + arg(0) + "," + arg(2) + ")";
    }
}
