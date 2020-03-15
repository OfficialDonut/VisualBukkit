package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;

@Category("Inventory")
@Description({"An inventory type", "Returns: inventory type"})
public class ExprInventoryType extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return InventoryType.class;
    }
}
