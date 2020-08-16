package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"Checks if a string can be parsed as a number", "Returns: boolean"})
public class ExprIsNumber extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(String.class, new ChoiceParameter("is", "is not"), "a number");
    }

    @Override
    protected String toNonNegatedJava() {
        BuildContext.addUtilMethod(UtilMethod.IS_NUMBER);
        return "UtilMethods.isNumber(" + arg(0) + ")";
    }
}
