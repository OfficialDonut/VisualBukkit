package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A new inventory", "Returns: inventory"})
public class ExprNewInventory extends ExpressionBlock<Inventory> {

    @Override
    protected Syntax init() {
        return new Syntax("new", InventoryType.class, "inventory named", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createInventory(null," + arg(0) + ",PluginMain.color(" + arg(1) + "))";
    }
}
