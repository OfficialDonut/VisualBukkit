package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The human entity that clicked in an InventoryInteractEvent", "Returns: human entity"})
public class ExprEventInventoryClicker extends ExpressionBlock<HumanEntity> {

    @Override
    protected Syntax init() {
        return new Syntax("inventory clicker");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getWhoClicked()";
    }
}
