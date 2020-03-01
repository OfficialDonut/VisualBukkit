package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if an object is a certain type", "Returns: boolean"})
public class ExprIsType extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Object.class, new ChoiceParameter("is a", "is not a"), Class.class);
    }

    @Override
    public String toJava() {
        return (isNegated() ? "!" : "") + arg(2) + ".isAssignableFrom(" + arg(0) + ".getClass())";
    }
}
