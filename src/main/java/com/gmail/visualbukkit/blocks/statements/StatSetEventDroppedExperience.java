package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityDeathEvent;

@Description("Sets the amount of experience dropped in an EntityDeathEvent")
public class StatSetEventDroppedExperience extends StatementBlock {

    public StatSetEventDroppedExperience() {
        init("set dropped experience to ", int.class);
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Set dropped experience must be used in an EntityDeathEvent", EntityDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.setDroppedExp(" + arg(0) + ");";
    }
}
