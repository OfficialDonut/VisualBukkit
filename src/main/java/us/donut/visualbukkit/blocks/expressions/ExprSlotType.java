package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;

@Category("Inventory")
@Description({"A slot type", "Returns: slot type"})
public class ExprSlotType extends EnumBlock<InventoryType.SlotType> {
}
