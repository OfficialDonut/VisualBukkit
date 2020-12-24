package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

@Description("The damaged item in a PlayerItemDamageEvent")
public class ExprEventDamagedItem extends ExpressionBlock<ItemStack> {

    public ExprEventDamagedItem() {
        init("damaged item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Damaged item must be used in a PlayerItemDamageEvent", PlayerItemDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItem()";
    }
}