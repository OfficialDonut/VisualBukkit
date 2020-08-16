package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryView;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The inventory view involved in an InventoryEvent", "Returns: inventory view"})
public class ExprEventInventoryView extends ExpressionBlock<InventoryView> {

    @Override
    protected Syntax init() {
        return new Syntax("event inventory view");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getView()";
    }
}
