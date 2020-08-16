package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.StringLiteralParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The return value of a function", "Returns: object"})
public class ExprFunctionValue extends ExpressionBlock<Object> {

    @Override
    protected Syntax init() {
        return new Syntax("function", new StringLiteralParameter(), List.class);
    }

    @Override
    public String toJava() {
        return "function(" + arg(0) + "," + arg(1) + ")";
    }
}
