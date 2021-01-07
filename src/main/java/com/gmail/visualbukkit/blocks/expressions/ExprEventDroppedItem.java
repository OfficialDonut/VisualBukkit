package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

@Description("The dropped item (entity) in a PlayerDropItemEvent or EntityDropItemEvent")
public class ExprEventDroppedItem extends ExpressionBlock<Item> {

    public ExprEventDroppedItem() {
        init("dropped item");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Dropped item must be used in a PlayerDropItemEvent or EntityDropItemEvent", PlayerDropItemEvent.class, EntityDropItemEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getItemDrop()";
    }
}
