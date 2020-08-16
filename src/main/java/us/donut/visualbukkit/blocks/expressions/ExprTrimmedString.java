package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Removes leading and trailing whitespace from a string", "Returns: string"})
public class ExprTrimmedString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "trimmed");
    }

    @Override
    public String toJava() {
        return arg(0) + ".trim()";
    }
}
