package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The human entity that clicked in an InventoryClickEvent", "Returns: human entity"})
@Event(InventoryInteractEvent.class)
public class ExprInventoryClicker extends ExpressionBlock<HumanEntity> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("inventory clicker");
    }

    @Override
    public String toJava() {
        return "event.getWhoClicked()";
    }
}
