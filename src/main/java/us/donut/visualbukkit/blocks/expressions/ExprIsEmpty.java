package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"Checks if a list is empty", "Returns: boolean"})
public class ExprIsEmpty extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(List.class, new ChoiceParameter("is", "is not"), "empty");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".isEmpty()";
    }
}
