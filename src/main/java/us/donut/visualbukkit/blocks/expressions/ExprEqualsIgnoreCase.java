package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Checks if a string equals another ignoring case", "Returns: boolean"})
public class ExprEqualsIgnoreCase extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("=", "!="), String.class, "ignoring case");
    }

    @Override
    public String toJava() {
        return (isNegated() ? "!" : "") + arg(0) + ".equalsIgnoreCase(" + arg(2) + ")";
    }
}
