package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"An item stack", "Returns: item stack"})
public class ExprNewItemStack extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("item stack of", Material.class);
    }

    @Override
    public String toJava() {
        return "new ItemStack(" + arg(0) + ")";
    }
}
