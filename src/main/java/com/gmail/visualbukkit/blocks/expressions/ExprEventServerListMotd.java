package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.annotations.Name;
import org.bukkit.event.server.ServerListPingEvent;

@Name("Event Server List MOTD")
@Description("The MOTD in a ServerListPingEvent")
public class ExprEventServerListMotd extends ExpressionBlock<String> {

    public ExprEventServerListMotd() {
        init("server list MOTD");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Server list MOTD must be used in a ServerListPingEvent", ServerListPingEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getMotd()";
    }
}
