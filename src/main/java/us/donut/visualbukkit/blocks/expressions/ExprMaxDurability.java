package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The max durability of a material", "Returns: number"})
public class ExprMaxDurability extends ExpressionBlock<Short> {

    @Override
    protected Syntax init() {
        return new Syntax("max durability of", Material.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getMaxDurability()";
    }
}
