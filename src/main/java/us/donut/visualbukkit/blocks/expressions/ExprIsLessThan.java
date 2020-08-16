package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a number is less than another number", "Returns: boolean"})
public class ExprIsLessThan extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax(double.class, new ChoiceParameter("<", "<="), double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + arg(1) + arg(2) + ")";
    }
}
