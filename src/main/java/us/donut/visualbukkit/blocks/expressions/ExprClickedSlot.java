package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The clicked slot in an InventoryClickEvent", "Returns: number"})
@Event(InventoryClickEvent.class)
public class ExprClickedSlot extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("clicked slot");
    }

    @Override
    public String toJava() {
        return "event.getSlot()";
    }

    @Override
    public Class<?> getReturnType() {
        return int.class;
    }
}
