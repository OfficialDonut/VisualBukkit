package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"A shaped recipe", "Returns: shaped recipe"})
public class ExprShapedRecipe extends ExpressionBlock<ShapedRecipe> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("shaped recipe for", ItemStack.class, "with ingredients",
                Material.class, ",", Material.class, ",", Material.class, ",",
                Material.class, ",", Material.class, ",", Material.class, ",",
                Material.class, ",", Material.class, ",", Material.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.getShapedRecipe(" + arg(0) + ",new Material[]{" +
                arg(1) + "," + arg(2) + "," + arg(3) + "," +
                arg(4) + "," + arg(5) + "," + arg(6) + "," +
                arg(7) + "," + arg(8) + "," + arg(9) + "})";
    }
}
