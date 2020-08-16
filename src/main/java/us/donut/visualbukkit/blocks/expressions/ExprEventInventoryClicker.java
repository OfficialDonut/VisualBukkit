package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The human entity that clicked in an InventoryClickEvent", "Returns: human entity"})
public class ExprEventInventoryClicker extends ExpressionBlock<HumanEntity> {

    @Override
    protected Syntax init() {
        return new Syntax("inventory clicker");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(InventoryClickEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getWhoClicked()";
    }
}
