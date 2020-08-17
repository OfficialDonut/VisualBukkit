package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a string contains a substring", "Returns: boolean"})
public class ExprStringContains extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new BinaryChoiceParameter("contains", "does not contain"), String.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".contains(" + arg(2) + ")";
    }
}
