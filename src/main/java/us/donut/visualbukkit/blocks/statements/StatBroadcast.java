package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Broadcasts a string")
public class StatBroadcast extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("broadcast", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.broadcastMessage(PluginMain.color(" + arg(0) + "));";
    }
}
