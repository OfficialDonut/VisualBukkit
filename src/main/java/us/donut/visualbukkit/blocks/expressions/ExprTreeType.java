package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.TreeType;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A tree type", "Returns: tree type"})
public class ExprTreeType extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return TreeType.class;
    }
}
