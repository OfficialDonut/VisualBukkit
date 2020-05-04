package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Sends an actionbar to a player")
public class StatSendActionbar extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("send actionbar", String.class, "to", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".spigot().sendMessage(" +
                "net.md_5.bungee.api.ChatMessageType.ACTION_BAR, " +
                "net.md_5.bungee.api.chat.TextComponent.fromLegacyText(PluginMain.color(" + arg(0) + ")));";
    }
}
