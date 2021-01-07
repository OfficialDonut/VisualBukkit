package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Item;

@Description("The pickup delay of a dropped item")
public class ExprDroppedItemPickupDelay extends ExpressionBlock<Integer> {

    public ExprDroppedItemPickupDelay() {
        init("pickup delay of ", Item.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPickupDelay()";
    }
}
