package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The type of slot that was clicked in an InventoryClickEvent", "Returns: slot type"})
@Event(InventoryClickEvent.class)
public class ExprEventSlotType extends ExpressionBlock<InventoryType.SlotType> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event slot type");
    }

    @Override
    public String toJava() {
        return "event.getSlotType()";
    }
}
