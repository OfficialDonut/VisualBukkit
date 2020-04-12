package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryView;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The inventory view involved in an event", "Returns: inventory view"})
@Event(InventoryEvent.class)
public class ExprEventInventoryView extends ExpressionBlock<InventoryView> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event inventory view");
    }

    @Override
    public String toJava() {
        return "event.getView()";
    }
}
