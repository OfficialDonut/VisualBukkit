package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

@Description("The bow item used in a EntityShootBowEvent")
public class ExprEventBowItem extends ExpressionBlock<ItemStack> {

    public ExprEventBowItem() {
        init("event bow item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event bow item must be used in an EntityShootBowEvent", EntityShootBowEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getBow()";
    }
}
