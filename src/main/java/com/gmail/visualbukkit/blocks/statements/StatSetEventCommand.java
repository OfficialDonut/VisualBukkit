package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.blocks.events.EvtServerCommandEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

@Description("Sets the command in a PlayerCommandPreprocessEvent or ServerCommandEvent")
public class StatSetEventCommand extends StatementBlock {

    public StatSetEventCommand() {
        init("set event command to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set event command must be used in a PlayerCommandPreprocessEvent or ServerCommandEvent", PlayerCommandPreprocessEvent.class, ServerCommandEvent.class);
    }

    @Override
    public String toJava() {
        return EvtServerCommandEvent.class.isAssignableFrom(getStructure().getClass()) ?
                "event.setMessage(" + arg(0) + ");" :
                "event.setCommand(" + arg(0) + ");";
    }
}
