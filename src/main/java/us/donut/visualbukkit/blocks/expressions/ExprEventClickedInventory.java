package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked inventory in an InventoryClickEvent", "Returns: inventory"})
public class ExprEventClickedInventory extends ExpressionBlock<Inventory> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked inventory");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getClickedInventory()";
    }
}
