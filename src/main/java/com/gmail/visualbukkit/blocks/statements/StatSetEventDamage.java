package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDamageEvent;

@Description("Sets the damage amount in an EntityDamageEvent")
public class StatSetEventDamage extends StatementBlock {

    public StatSetEventDamage() {
        init("set damage to ", double.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set damage must be used in an EntityDamageEvent", EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setDamage(" + arg(0) + ");";
    }
}
