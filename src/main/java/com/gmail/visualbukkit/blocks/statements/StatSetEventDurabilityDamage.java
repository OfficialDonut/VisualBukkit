package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerItemDamageEvent;

@Description("Sets the durability damage in a PlayerItemDamageEvent")
public class StatSetEventDurabilityDamage extends StatementBlock {

    public StatSetEventDurabilityDamage() {
        init("set durability damage to ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set durability damage must be used in a PlayerItemDamageEvent", PlayerItemDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setDamage(" + arg(0) + ");";
    }
}
