package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Sound;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A sound", "Returns: sound"})
public class ExprSound extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return Sound.class;
    }
}
