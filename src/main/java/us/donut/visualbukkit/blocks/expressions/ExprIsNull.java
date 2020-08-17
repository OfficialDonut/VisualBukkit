package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an object is null", "Returns: boolean"})
public class ExprIsNull extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Object.class, new BinaryChoiceParameter("is", "is not"), "null");
    }

    @Override
    protected String toNonNegatedJava() {
        return "(" + arg(0) + "== null)";
    }

    @Override
    protected String toNegatedJava() {
        return "(" + arg(0) + "!= null)";
    }
}
