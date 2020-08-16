package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The clicked slot in an InventoryClickEvent", "Returns: number"})
public class ExprEventClickedSlot extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("clicked slot");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSlot()";
    }
}
