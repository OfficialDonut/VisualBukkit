package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Item Stack")
@Description({"An item stack", "Returns: item stack"})
public class ExprItemStack extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("item stack of", Material.class);
    }

    @Override
    public String toJava() {
        return "new ItemStack(" + arg(0) + ")";
    }

    @Override
    public Class<?> getReturnType() {
        return ItemStack.class;
    }
}
