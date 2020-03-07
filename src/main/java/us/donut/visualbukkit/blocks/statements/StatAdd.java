package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.*;
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
        ChangeableExpressionBlock changeable = (ChangeableExpressionBlock) ((ExpressionParameter) getParameter(1)).getExpression();
        ExpressionBlock deltaExpr = ((ExpressionParameter) getParameter(0)).getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), changeable.getDeltaType(ChangeType.ADD), deltaExpr.toJava());
        String java = changeable.change(ChangeType.ADD, delta);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
