package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"Checks if two objects are equal", "Returns: boolean"})
public class ExprEquals extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Object.class, new BinaryChoiceParameter("=", "!="), Object.class);
    }

    @Override
    protected String toNonNegatedJava() {
        BuildContext.addUtilMethod(UtilMethod.CHECK_EQUALS);
        return "UtilMethods.checkEquals(" + arg(0) + "," + arg(2) + ")";
    }
}
