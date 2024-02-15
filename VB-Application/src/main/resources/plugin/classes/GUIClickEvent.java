import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final GUIIdentifier identifier;
    private final InventoryClickEvent inventoryClickEvent;

    public GUIClickEvent(GUIIdentifier identifier, InventoryClickEvent inventoryClickEvent) {
        this.identifier = identifier;
        this.inventoryClickEvent = inventoryClickEvent;
    }

    public String getID() {
        return identifier.getID();
    }

    public InventoryClickEvent getInventoryClickEvent() {
        return inventoryClickEvent;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
