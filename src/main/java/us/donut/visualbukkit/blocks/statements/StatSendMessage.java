package us.donut.visualbukkit.blocks.statements;

import org.bukkit.command.CommandSender;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Sends a message to a player or console")
@Category(StatementCategory.PLAYER)
public class StatSendMessage extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("send", String.class, "to", CommandSender.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".sendMessage(PluginMain.color(" + arg(0) + "));";
    }
}
