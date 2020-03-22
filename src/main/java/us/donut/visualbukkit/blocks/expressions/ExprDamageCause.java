package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDamageEvent;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A damage cause", "Returns: damage cause"})
public class ExprDamageCause extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return EntityDamageEvent.DamageCause.class;
    }
}
