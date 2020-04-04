package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The inventory involved in an event", "Returns: inventory"})
@Event(InventoryEvent.class)
public class ExprEventInventory extends ExpressionBlock<Inventory> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event inventory");
    }

    @Override
    public String toJava() {
        return "event.getInventory()";
    }
}
