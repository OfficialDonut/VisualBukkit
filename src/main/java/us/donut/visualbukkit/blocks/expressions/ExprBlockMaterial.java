package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

import java.util.Arrays;

@Description({"A block material", "Returns: material"})
public class ExprBlockMaterial extends EnumBlock<Material> {

    @Override
    @SuppressWarnings("deprecation")
    protected String[] computeConstants() {
        return Arrays.stream(Material.values())
                .filter(value -> !value.isLegacy() && value.isBlock())
                .map(Enum::name)
                .sorted()
                .toArray(String[]::new);
    }
}
