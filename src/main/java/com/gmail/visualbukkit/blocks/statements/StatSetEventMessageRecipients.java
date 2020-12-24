package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

@Description("Sets the message recipients in an AsyncPlayerChatEvent")
public class StatSetEventMessageRecipients extends StatementBlock {

    public StatSetEventMessageRecipients() {
        init("set message recipients to ", List.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set message recipients must be used in an AsyncPlayerChatEvent", AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getRecipients().clear();" +
                "event.getRecipients().addAll(" + arg(0) + ");";
    }
}
