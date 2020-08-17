package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a string equals another ignoring case", "Returns: boolean"})
public class ExprEqualsIgnoreCase extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new BinaryChoiceParameter("=", "!="), String.class, "ignoring case");
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".equalsIgnoreCase(" + arg(2) + ")";
    }
}
