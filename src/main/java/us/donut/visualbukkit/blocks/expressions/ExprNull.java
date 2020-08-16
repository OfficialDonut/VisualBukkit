package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An empty value", "Returns: null"})
public class ExprNull extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("null");
    }

    @Override
    public String toJava() {
        return "((Object) null)";
    }
}
