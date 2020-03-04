package us.donut.visualbukkit.blocks.expressions;

import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.FunctionPane;
import us.donut.visualbukkit.editor.ProcedurePane;

@Description("An argument of a procedure or function")
public class ExprArgument extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("arg", int.class);
    }

    @Override
    public String toJava() {
        BlockPane blockPane = getBlockPane();
        if (blockPane instanceof ProcedurePane || blockPane instanceof FunctionPane) {
            return "args[" + arg(0) + "]";
        }
        throw new IllegalStateException();
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }
}
