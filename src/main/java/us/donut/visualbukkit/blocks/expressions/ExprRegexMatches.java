package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a string matches a regex", "Returns: boolean"})
public class ExprRegexMatches extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new BinaryChoiceParameter("matches", "does not match"), "regex", String.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".matches(" + arg(2) + ")";
    }
}
