package us.donut.visualbukkit.blocks.statements;

import org.bukkit.command.CommandSender;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Sends a message to a player or console")
public class StatMessage extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("send", String.class, "to", CommandSender.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".sendMessage(PluginMain.color(" + arg(0) + "));";
    }
}
