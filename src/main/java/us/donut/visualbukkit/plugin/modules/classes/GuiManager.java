package us.donut.visualbukkit.plugin.modules.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GuiManager implements Listener {

    private static GuiManager instance = new GuiManager();
    private Map<String, Supplier<Inventory>> guis = new HashMap<>();
    private Map<Player, Map<String, Inventory>> playerGuis = new HashMap<>();

    public void register(String gui, Supplier<Inventory> supplier) {
        guis.put(gui, supplier);
    }

    public void open(String gui, Player player) {
        player.openInventory(playerGuis
                .computeIfAbsent(player, k -> new HashMap<>())
                .computeIfAbsent(gui, k -> guis.get(gui).get()));
    }

    public boolean isGui(String gui, InventoryEvent e) {
        if (e instanceof InventoryClickEvent) {
            Inventory inventory = ((InventoryClickEvent) e).getClickedInventory();
            return inventory != null &&
                    inventory.getHolder() instanceof GuiIdentifier &&
                    ((GuiIdentifier) inventory.getHolder()).getGui().equals(gui) &&
                    e.getView().getTopInventory().equals(inventory);
        } else {
            return e.getInventory().getHolder() instanceof GuiIdentifier &&
                    ((GuiIdentifier) e.getInventory().getHolder()).getGui().equals(gui);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTopInventory().getHolder() instanceof GuiIdentifier) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerGuis.remove(e.getPlayer());
    }

    public static GuiManager getInstance() {
        return instance;
    }
}
