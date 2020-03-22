package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.BlockFace;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

@Description({"A block face", "Returns: block face"})
public class ExprBlockFace extends EnumBlock {

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return BlockFace.class;
    }
}
