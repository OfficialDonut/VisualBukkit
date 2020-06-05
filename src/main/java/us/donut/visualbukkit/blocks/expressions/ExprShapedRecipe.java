package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.PluginMain;

import java.util.UUID;

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
        return "getShapedRecipe(" + arg(0) + ",new Material[]{" +
                arg(1) + "," + arg(2) + "," + arg(3) + "," +
                arg(4) + "," + arg(5) + "," + arg(6) + "," +
                arg(7) + "," + arg(8) + "," + arg(9) + "})";
    }

    @UtilMethod
    public static ShapedRecipe getShapedRecipe(ItemStack result, Material... ingredients) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(PluginMain.getInstance(), UUID.randomUUID().toString()), result);
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                if (i < 3) {
                    recipe.shape("012");
                } else if (i < 6) {
                    recipe.shape("012", "345");
                } else {
                    recipe.shape("012", "345", "678");
                }
            }
        }
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                recipe.setIngredient(Character.forDigit(i, 10), ingredient);
            }
        }
        return recipe;
    }
}
