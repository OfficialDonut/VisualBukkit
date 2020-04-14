package us.donut.visualbukkit.blocks.statements;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Creates a world")
public class StatCreateWorld extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("create world with", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".createWorld();";
    }
}
