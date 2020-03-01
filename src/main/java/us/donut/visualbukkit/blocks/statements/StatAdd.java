package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Add To Expression")
@Description("Adds a value to an expression")
public class StatAdd extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("add", Object.class, "to", Object.class);
    }

    @Override
    public String toJava() {
        ExpressionBlock addExpr = ((ExpressionParameter) getParameter(1)).getExpression();
        ExpressionBlock deltaExpr = ((ExpressionParameter) getParameter(0)).getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), addExpr.getReturnType(), deltaExpr.toJava());
        String java = ((ExpressionBlock.Changeable) addExpr).change(ExpressionBlock.ChangeType.ADD, delta);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
