package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.PlayerDeathEvent;

@Description("Sets the death message in a PlayerDeathEvent")
public class StatSetEventDeathMessage extends StatementBlock {

    public StatSetEventDeathMessage() {
        init("set death message to ", String.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set death message must be used in a PlayerDeathEvent", PlayerDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setDeathMessage(PluginMain.color(" + arg(0) + "));";
    }
}
