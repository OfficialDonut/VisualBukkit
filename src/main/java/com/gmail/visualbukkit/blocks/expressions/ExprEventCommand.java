package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.events.EvtServerCommandEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

@Description("The command in a PlayerCommandPreprocessEvent or ServerCommandEvent")
public class ExprEventCommand extends ExpressionBlock<String> {

    public ExprEventCommand() {
        init("event command");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event command must be used in a PlayerCommandPreprocessEvent or ServerCommandEvent", PlayerCommandPreprocessEvent.class, ServerCommandEvent.class);
    }

    @Override
    public String toJava() {
        return EvtServerCommandEvent.class.isAssignableFrom(getStatement().getStructure().getClass()) ?
                "event.getCommand()" :
                "event.getMessage()";
    }
}