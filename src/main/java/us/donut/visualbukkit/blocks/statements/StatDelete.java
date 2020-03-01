package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Name("Delete Expression")
@Description("Deletes an expression")
public class StatDelete extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("delete", Object.class);
    }

    @Override
    public String toJava() {
        ExpressionBlock.Changeable deleteExpr = (ExpressionBlock.Changeable) ((ExpressionParameter) getParameter(0)).getExpression();
        String java = deleteExpr.change(ExpressionBlock.ChangeType.DELETE, null);
        if (java != null) {
            return java;
        }
        throw new IllegalStateException();
    }
}
