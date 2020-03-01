package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Checks if a string contains a substring", "Returns: boolean"})
public class ExprStringContains extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("contains", "does not contain"), String.class);
    }

    @Override
    public String toJava() {
        return (isNegated() ? "!" : "") + arg(0) + ".contains(" + arg(2) + ")";
    }
}
