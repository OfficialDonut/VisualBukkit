package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The action in an InventoryClickEvent", "Returns: inventory action"})
public class ExprEventInventoryAction extends ExpressionBlock<InventoryAction> {

    @Override
    protected Syntax init() {
        return new Syntax("event inventory action");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getAction()";
    }
}
