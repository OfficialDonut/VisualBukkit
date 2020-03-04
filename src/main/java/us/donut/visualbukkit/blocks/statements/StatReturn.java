package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.FunctionPane;

@Description("Returns a value from a function")
public class StatReturn extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("return", Object.class);
    }

    @Override
    public String toJava() {
        if (getBlockPane() instanceof FunctionPane) {
            return "return " + arg(0) + ";";
        }
        throw new IllegalStateException();
    }
}
