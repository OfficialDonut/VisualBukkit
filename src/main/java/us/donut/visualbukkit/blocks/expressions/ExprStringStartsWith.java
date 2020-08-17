package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a string starts with another string", "Returns: boolean"})
public class ExprStringStartsWith extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new BinaryChoiceParameter("does", "does not"), "start with", String.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".startsWith(" + arg(2) + ")";
    }
}
