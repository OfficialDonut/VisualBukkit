package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Item;

@Description("Sets the pickup delay of a dropped item")
public class StatSetDroppedItemPickupDelay extends StatementBlock {

    public StatSetDroppedItemPickupDelay() {
        init("set pickup delay of ", Item.class, " to ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setPickupDelay(" + arg(1) + ");";
    }
}
