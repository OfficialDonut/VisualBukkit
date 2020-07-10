package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Description({"Checks if two objects are equal", "Returns: boolean"})
public class ExprEquals extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Object.class, new ChoiceParameter("=", "!="), Object.class);
    }

    @Override
    protected String toNonNegatedJava() {
        BuildContext.addUtilMethod("checkEquals");
        return "UtilMethods.checkEquals(" + arg(0) + "," + arg(2) + ")";
    }
}
