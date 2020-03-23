package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import us.donut.visualbukkit.blocks.EnumBlock;
import us.donut.visualbukkit.blocks.annotations.Description;

import java.util.ArrayList;
import java.util.List;

@Description({"A block material", "Returns: material"})
public class ExprBlockMaterial extends EnumBlock {

    @Override
    protected String[] computeConstants() {
        List<String> materials = new ArrayList<>();
        for (Material value : Material.values()) {
            if (!value.isLegacy() && value.isBlock()) {
                materials.add(value.name());
            }
        }
        return materials.toArray(new String[0]);
    }

    @Override
    public Class<? extends Enum<?>> getEnum() {
        return Material.class;
    }
}
