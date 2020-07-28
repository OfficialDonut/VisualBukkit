package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("String")
@Description({"Checks if a string starts with another string", "Returns: boolean"})
public class ExprStringStartsWith extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("does", "does not"), "start with", String.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".startsWith(" + arg(2) + ")";
    }
}
