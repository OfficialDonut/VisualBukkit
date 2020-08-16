package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"Checks if an object is a certain type", "Returns: boolean"})
public class ExprIsType extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Object.class, new ChoiceParameter("is a", "is not a"), Class.class);
    }

    @Override
    protected String toNonNegatedJava() {
        BuildContext.addUtilMethod(UtilMethod.IS_TYPE);
        return "UtilMethods.isType(" + arg(0) + "," + arg(2) + ")";
    }
}
