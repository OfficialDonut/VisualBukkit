package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.Statement;
import org.bukkit.event.Cancellable;

public class StatCancelEvent extends Statement {

    public StatCancelEvent() {
        super("stat-cancel-event");
    }

    @Override
    public Block createBlock() {
        return new Block(this) {
            @Override
            public void update() {
                super.update();
                checkForEvent(Cancellable.class);
            }

            @Override
            public String toJava() {
                return "event.setCancelled(true);";
            }
        };
    }
}
