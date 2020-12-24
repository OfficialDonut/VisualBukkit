package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;

@Category(Category.IO)
@Description("Sends an actionbar message to a player")
public class StatSendActionbar extends StatementBlock {

    public StatSendActionbar() {
        init("send actionbar ", String.class, " to ", Player.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".spigot().sendMessage(" +
                "net.md_5.bungee.api.ChatMessageType.ACTION_BAR, " +
                "net.md_5.bungee.api.chat.TextComponent.fromLegacyText(PluginMain.color(" + arg(0) + ")));";
    }
}
