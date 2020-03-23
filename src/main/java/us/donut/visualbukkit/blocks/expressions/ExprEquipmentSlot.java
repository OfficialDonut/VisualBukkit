package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.EquipmentSlot;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"An equipment slot", "Returns: equipment slot"})
public class ExprEquipmentSlot extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return EquipmentSlot.class;
    }
}
