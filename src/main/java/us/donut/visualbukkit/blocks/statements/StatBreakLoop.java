package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Breaks a loop")
public class StatBreakLoop extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("break;");
    }

    @Override
    public String toJava() {
        return "break;";
    }
}
