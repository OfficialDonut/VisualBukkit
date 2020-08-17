package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"Checks if a list contains an object", "Returns: boolean"})
public class ExprListContains extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(List.class, new BinaryChoiceParameter("contains", "does not contain"), Object.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".contains(" + arg(2) + ")";
    }
}
