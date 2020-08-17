package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a number is greater than another number", "Returns: boolean"})
public class ExprIsGreaterThan extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, new BinaryChoiceParameter(">", ">="), double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + arg(1) + arg(2) + ")";
    }
}
