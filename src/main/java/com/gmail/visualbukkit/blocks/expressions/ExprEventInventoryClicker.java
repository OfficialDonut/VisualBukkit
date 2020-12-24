package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;

@Description("The player who clicked the inventory in an InventoryInteractEvent")
public class ExprEventInventoryClicker extends ExpressionBlock<Player> {

    public ExprEventInventoryClicker() {
        init("inventory clicker");
    }

    @Override
    public void update() {
        super.update();
        validateEvent("Inventory clicker must be used in an InventoryInteractEvent", InventoryInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "((Player) event.getWhoClicked())";
    }
}