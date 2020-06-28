package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description("Randomly shuffles a list")
public class StatShuffleList extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("shuffle", SimpleList.class);
    }

    @Override
    public String toJava() {
        return "Collections.shuffle(" + arg(0) + ");";
    }
}
