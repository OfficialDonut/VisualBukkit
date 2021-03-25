import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickEvent extends InventoryClickEvent {

    private static final HandlerList handlers = new HandlerList();
    private String id;

    public GUIClickEvent(InventoryClickEvent e, String id) {
        super(e.getView(), e.getSlotType(), e.getSlot(), e.getClick(), e.getAction(), e.getHotbarButton());
        this.id = id;
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
