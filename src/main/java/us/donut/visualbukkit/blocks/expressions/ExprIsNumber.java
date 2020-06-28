package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if a string can be parsed as a number", "Returns: boolean"})
public class ExprIsNumber extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(String.class, new ChoiceParameter("is", "is not"), "a number");
    }

    @Override
    protected String toNonNegatedJava() {
        return "isNumber(" + arg(0) + ")";
    }

    @UtilMethod
    public static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
