package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if a number is greater than another number", "Returns: boolean"})
public class ExprIsGreaterThan extends ExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(double.class, new ChoiceParameter(">", ">="), double.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + arg(1) + arg(2) + ")";
    }
}
