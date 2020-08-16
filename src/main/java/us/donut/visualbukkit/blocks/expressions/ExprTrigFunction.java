package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A trigonometric function (radians)", "Returns: number"})
public class ExprTrigFunction extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax(new ChoiceParameter("cos", "sin", "tan", "acos", "asin", "atan"), "(", double.class, ")");
    }

    @Override
    public String toJava() {
        return "Math." + arg(0) + "(" + arg(1) + ")";
    }
}
