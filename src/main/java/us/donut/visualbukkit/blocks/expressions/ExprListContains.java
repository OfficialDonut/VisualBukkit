package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"Checks if a list contains an object", "Returns: boolean"})
public class ExprListContains extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(SimpleList.class, new ChoiceParameter("contains", "does not contain"), Object.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".contains(" + arg(2) + ")";
    }
}
