package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.StatementCategory;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Name;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Name("Send JSON Message")
@Description("Sends a JSON message to a player")
@Category(StatementCategory.PLAYER)
public class StatSendJSONMessage extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("send JSON", String.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(" + arg(0) + "));";
    }
}
