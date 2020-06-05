package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if two objects are equal", "Returns: boolean"})
public class ExprEquals extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(Object.class, new ChoiceParameter("=", "!="), Object.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return "checkEquals(" + arg(0) + "," + arg(2) + ")";
    }

    @UtilMethod
    public static boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
    }
}
