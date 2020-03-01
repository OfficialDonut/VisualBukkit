package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Set Expression")
@Description("Sets an expression to a value")
public class StatSet extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("set", Object.class, "to", Object.class);
    }

    @Override
    public String toJava() {
        ExpressionBlock setExpr = ((ExpressionParameter) getParameter(0)).getExpression();
        ExpressionBlock deltaExpr = ((ExpressionParameter) getParameter(1)).getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), setExpr.getReturnType(), deltaExpr.toJava());
        String java = ((ExpressionBlock.Changeable) setExpr).change(ExpressionBlock.ChangeType.SET, delta);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
