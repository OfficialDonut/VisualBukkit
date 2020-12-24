package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@Description("The interact action in a PlayerInteractEvent")
public class ExprEventInteractAction extends ExpressionBlock<Action> {

    public ExprEventInteractAction() {
        init("interact action");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Interact action must be used in a PlayerInteractEvent", PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getAction()";
    }
}