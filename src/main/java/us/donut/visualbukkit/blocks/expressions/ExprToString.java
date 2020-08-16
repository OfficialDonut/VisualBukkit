package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Converts an object to a string", "Returns: string"})
public class ExprToString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(Object.class, "to string");
    }

    @Override
    public String toJava() {
        return "String.valueOf(" + arg(0) + ")";
    }
}
