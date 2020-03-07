package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Remove From Expression")
@Description("Removes a value from an expression")
public class StatRemove extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("remove", Object.class, "from", Object.class);
    }

    @Override
    public String toJava() {
        ChangeableExpressionBlock changeable = (ChangeableExpressionBlock) ((ExpressionParameter) getParameter(1)).getExpression();
        ExpressionBlock deltaExpr = ((ExpressionParameter) getParameter(0)).getExpression();
        String delta = TypeHandler.convert(deltaExpr.getReturnType(), changeable.getDeltaType(ChangeType.REMOVE), deltaExpr.toJava());
        String java = changeable.change(ChangeType.REMOVE, delta);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
