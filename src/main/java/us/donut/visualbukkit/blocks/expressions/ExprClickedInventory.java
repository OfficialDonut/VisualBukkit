package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The clicked inventory in an InventoryClickEvent", "Returns: inventory"})
@Event(InventoryClickEvent.class)
public class ExprClickedInventory extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clicked inventory");
    }

    @Override
    public String toJava() {
        return "event.getClickedInventory()";
    }

    @Override
    public Class<?> getReturnType() {
        return Inventory.class;
    }
}
