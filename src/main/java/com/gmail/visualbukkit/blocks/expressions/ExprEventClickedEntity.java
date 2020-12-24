package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

@Description("The clicked entity in an PlayerInteractEntityEvent")
public class ExprEventClickedEntity extends ExpressionBlock<Entity> {

    public ExprEventClickedEntity() {
        init("clicked entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Clicked entity must be used in a PlayerInteractEntityEvent", PlayerInteractEntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getRightClicked()";
    }
}