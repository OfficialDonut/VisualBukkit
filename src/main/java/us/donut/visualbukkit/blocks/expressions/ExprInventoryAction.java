package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryAction;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;

@Category("Inventory")
@Description({"An inventory action", "Returns: inventory action"})
public class ExprInventoryAction extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return InventoryAction.class;
    }
}
