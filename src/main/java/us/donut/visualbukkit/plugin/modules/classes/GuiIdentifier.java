package us.donut.visualbukkit.plugin.modules.classes;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiIdentifier implements InventoryHolder {

    private String gui;

    public GuiIdentifier(String gui) {
        this.gui = gui;
    }

    public String getGui() {
        return gui;
    }

    @Override
    public Inventory getInventory() {
        throw new UnsupportedOperationException();
    }
}
