package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if two booleans are both true", "Returns: boolean"})
public class ExprAnd extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(boolean.class, "and", boolean.class);
    }

    @Override
    public String toJava() {
        return "(" + arg(0) + "&&" + arg(1) + ")";
    }
}
