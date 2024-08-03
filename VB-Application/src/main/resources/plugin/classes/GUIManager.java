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

    private static final GUIManager instance = new GUIManager();
    private final Map<String, GUI> guis = new HashMap<>();

    private GUIManager() {}

    public void register(String id, boolean recreateWhenOpened, Function<Player, Inventory> createFunction) {
        guis.put(id, new GUI(createFunction, recreateWhenOpened));
    }

    public void open(String id, Player player) {
        guis.get(id).open(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        InventoryHolder holder = e.getView().getTopInventory().getHolder();
        if (holder instanceof GUIIdentifier) {
            GUIIdentifier id = (GUIIdentifier) holder;
            e.setCancelled(true);
            if (e.getView().getTopInventory().equals(e.getClickedInventory())) {
                Bukkit.getPluginManager().callEvent(new GUIClickEvent(id, e));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        for (GUI gui : guis.values()) {
            gui.inventories.remove(e.getPlayer());
        }
    }

    public static GUIManager getInstance() {
        return instance;
    }

    private static class GUI {

        private final Map<Player, Inventory> inventories = new HashMap<>();
        private final Function<Player, Inventory> createFunction;
        private final boolean recreateWhenOpened;

        public GUI(Function<Player, Inventory> createFunction, boolean recreateWhenOpened) {
            this.createFunction = createFunction;
            this.recreateWhenOpened = recreateWhenOpened;
        }

        public void open(Player player) {
            player.openInventory(recreateWhenOpened ? createFunction.apply(player) : inventories.computeIfAbsent(player, createFunction));
        }
    }
}
