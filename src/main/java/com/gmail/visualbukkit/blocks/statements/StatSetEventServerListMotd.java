package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.event.server.ServerListPingEvent;

@Name("Set Event Server List MOTD")
@Description("Sets the MOTD in a ServerListPingEvent")
public class StatSetEventServerListMotd extends StatementBlock {

    public StatSetEventServerListMotd() {
        init("set server list MOTD to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set server list MOTD must be used in a ServerListPingEvent", ServerListPingEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setMotd(" + arg(0) + ");";
    }
}
