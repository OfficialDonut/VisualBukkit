package com.gmail.visualbukkit.stdlib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GUIManager implements Listener {

    private static GUIManager instance = new GUIManager();
    private Map<String, Function<Player, Inventory>> guiCreators = new HashMap<>();
    private Map<Player, Map<String, Inventory>> playerGuis = new HashMap<>();

    public void register(String id, Function<Player, Inventory> creator) {
        guiCreators.put(id, creator);
    }

    public void open(String id, Player player) {
        player.openInventory(playerGuis
                .computeIfAbsent(player, k -> new HashMap<>())
                .computeIfAbsent(id, k -> guiCreators.get(id).apply(player)));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getView().getTopInventory().getHolder();
        if (holder instanceof GUIIdentifier) {
            e.setCancelled(true);
            if (e.getView().getTopInventory().equals(e.getClickedInventory())) {
                Bukkit.getPluginManager().callEvent(new GUIClickEvent(e, ((GUIIdentifier) holder).getID()));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerGuis.remove(e.getPlayer());
    }

    public static GUIManager getInstance() {
        return instance;
    }
}
