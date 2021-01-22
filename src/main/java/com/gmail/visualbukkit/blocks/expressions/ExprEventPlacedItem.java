package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

@Description("The item for the placed block in a BlockPlaceEvent")
public class ExprEventPlacedItem extends ExpressionBlock<ItemStack> {

    public ExprEventPlacedItem() {
        init("placed item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Placed item must be used in a BlockPlaceEvent", BlockPlaceEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItemInHand()";
    }
}
