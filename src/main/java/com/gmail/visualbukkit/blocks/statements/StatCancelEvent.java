package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.Cancellable;

@Description("Cancels an event")
public class StatCancelEvent extends StatementBlock {

    public StatCancelEvent() {
        init("cancel event");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Cancel event must be used in a cancellable event", Cancellable.class);
    }

    @Override
    public String toJava() {
        return "event.setCancelled(true);";
    }
}
