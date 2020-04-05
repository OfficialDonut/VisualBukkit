package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Clear Expression")
@Description("Clears an expression")
public class StatClear extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clear", Object.class);
    }

    @Override
    public String toJava() {
        ChangeableExpressionBlock<?> clearExpr = (ChangeableExpressionBlock<?>) ((ExpressionParameter) getParameter(0)).getExpression();
        String java = clearExpr.change(ChangeType.CLEAR, null);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
