package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"A shaped recipe", "Returns: shaped recipe"})
public class ExprShapedRecipe extends ExpressionBlock<ShapedRecipe> {

    @Override
    protected Syntax init() {
        return new Syntax("shaped recipe for", ItemStack.class, "with ingredients",
                Material.class, ",", Material.class, ",", Material.class, ",",
                Material.class, ",", Material.class, ",", Material.class, ",",
                Material.class, ",", Material.class, ",", Material.class);
    }

    @Override
    public String toJava() {
        BuildContext.addUtilMethod(UtilMethod.GET_SHAPED_RECIPE);
        return "UtilMethods.getShapedRecipe(PluginMain.getInstance()," + arg(0) + "," +
                arg(1) + "," + arg(2) + "," + arg(3) + "," +
                arg(4) + "," + arg(5) + "," + arg(6) + "," +
                arg(7) + "," + arg(8) + "," + arg(9) + ")";
    }
}
