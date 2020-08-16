package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Makes a string all uppercase", "Returns: string"})
public class ExprUppercaseString extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, "in uppercase");
    }

    @Override
    public String toJava() {
        return arg(0) + ".toUpperCase()";
    }
}
