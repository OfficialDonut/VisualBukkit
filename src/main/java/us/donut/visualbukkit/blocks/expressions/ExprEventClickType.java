package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The click type in an InventoryClickEvent", "Returns: click type"})
@Event(InventoryClickEvent.class)
public class ExprEventClickType extends ExpressionBlock<ClickType> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event click type");
    }

    @Override
    public String toJava() {
        return "event.getClick()";
    }
}
