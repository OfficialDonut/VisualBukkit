package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Description({"Checks if a string can be parsed as a number", "Returns: boolean"})
public class ExprIsNumber extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("is", "is not"), "a number");
    }

    @Override
    protected String toNonNegatedJava() {
        BuildContext.addUtilMethod("isNumber");
        return "UtilMethods.isNumber(" + arg(0) + ")";
    }
}
