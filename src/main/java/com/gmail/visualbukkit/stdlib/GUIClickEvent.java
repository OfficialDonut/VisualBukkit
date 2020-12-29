package com.gmail.visualbukkit.stdlib;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private InventoryClickEvent inventoryClickEvent;
    private String id;

    public GUIClickEvent(InventoryClickEvent inventoryClickEvent, String id) {
        this.inventoryClickEvent = inventoryClickEvent;
        this.id = id;
    }

    public InventoryClickEvent getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    public String getID() {
        return id;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
