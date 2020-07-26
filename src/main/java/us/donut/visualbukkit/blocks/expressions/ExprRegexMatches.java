package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if a string matches a regex", "Returns: boolean"})
public class ExprRegexMatches extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("matches", "does not match"), "regex", String.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".matches(" + arg(2) + ")";
    }
}
