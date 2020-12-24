package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

@Description("The item in a PlayerInteractEvent or PlayerItemConsumeEvent")
public class ExprEventItem extends ExpressionBlock<ItemStack> {

    public ExprEventItem() {
        init("event item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event item must be used in a PlayerInteractEvent or PlayerItemConsumeEvent", PlayerInteractEvent.class, PlayerItemConsumeEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItem()";
    }
}