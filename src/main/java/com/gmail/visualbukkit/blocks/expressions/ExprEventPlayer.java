package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.EventBlock;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerEvent;

@Description("The player in a PlayerEvent")
public class ExprEventPlayer extends ExpressionBlock<Player> {

    public ExprEventPlayer() {
        init("event player");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Event player must be used in a PlayerEvent",
                PlayerEvent.class, BlockBreakEvent.class, BlockCanBuildEvent.class, BlockDamageEvent.class,
                BlockPlaceEvent.class, SignChangeEvent.class, InventoryCloseEvent.class, InventoryOpenEvent.class);
    }

    @Override
    public String toJava() {
        return InventoryEvent.class.isAssignableFrom(((EventBlock) getStatement().getStructure()).getEvent()) ?
                "((Player) event.getPlayer())" :
                "event.getPlayer()";
    }
}