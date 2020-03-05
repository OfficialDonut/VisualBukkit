package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Advances loop to next iteration")
public class StatContinueLoop extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("continue");
    }

    @Override
    public String toJava() {
        return "continue;";
    }
}
