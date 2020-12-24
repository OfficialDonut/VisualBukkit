package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

@Description("The player who died in a PlayerDeathEvent")
public class ExprEventKilledPlayer extends ExpressionBlock<Player> {

    public ExprEventKilledPlayer() {
        init("killed player");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Killed player must be used in a PlayerDeathEvent", PlayerDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getEntity()";
    }
}