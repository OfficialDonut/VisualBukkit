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
    private Map<String, GUI> guis = new HashMap<>();
    private Map<Player, Map<String, Inventory>> caches = new HashMap<>();

    public void register(String id, Function<Player, Inventory> creator, boolean cache) {
        guis.put(id, new GUI(creator, cache));
    }

    public void open(String id, Player player) {
        GUI gui = guis.get(id);
        player.openInventory(gui.cache ? caches.computeIfAbsent(player, k -> new HashMap<>()).computeIfAbsent(id, k -> gui.creator.apply(player)) : gui.creator.apply(player));
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
        caches.remove(e.getPlayer());
    }

    public static GUIManager getInstance() {
        return instance;
    }

    private static class GUI {

        private Function<Player, Inventory> creator;
        private boolean cache;

        public GUI(Function<Player, Inventory> creator, boolean cache) {
            this.creator = creator;
            this.cache = cache;
        }
    }
}
