package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Category("Inventory")
@Description({"The action in an InventoryClickEvent", "Returns: inventory action"})
@Event(InventoryClickEvent.class)
public class ExprEventInventoryAction extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event inventory action");
    }

    @Override
    public String toJava() {
        if (InventoryClickEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            return "event.getAction()";
        }
        throw new IllegalStateException();
    }

    @Override
    public Class<?> getReturnType() {
        return InventoryAction.class;
    }
}
