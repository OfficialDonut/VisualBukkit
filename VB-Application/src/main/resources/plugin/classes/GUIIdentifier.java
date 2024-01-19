import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIIdentifier implements InventoryHolder {

    private final String id;

    public GUIIdentifier(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    @Override
    public Inventory getInventory() {
        throw new UnsupportedOperationException();
    }
}
