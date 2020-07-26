package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description("Randomly shuffles a list")
public class StatShuffleList extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("shuffle", List.class);
    }

    @Override
    public String toJava() {
        return "Collections.shuffle(" + arg(0) + ");";
    }
}
