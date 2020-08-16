package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The type of slot that was clicked in an InventoryClickEvent", "Returns: slot type"})
public class ExprEventSlotType extends ExpressionBlock<InventoryType.SlotType> {

    @Override
    protected Syntax init() {
        return new Syntax("event slot type");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSlotType()";
    }
}
