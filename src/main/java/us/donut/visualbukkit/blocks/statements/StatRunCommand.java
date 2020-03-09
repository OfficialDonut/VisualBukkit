package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Runs a command through console")
public class StatRunCommand extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("run command", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.dispatchCommand(Bukkit.getConsoleSender()," + arg(0) + ");";
    }
}
