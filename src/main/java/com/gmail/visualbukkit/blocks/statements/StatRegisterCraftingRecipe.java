package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Description("Registers a crafting recipe")
public class StatRegisterCraftingRecipe extends StatementBlock {

    public StatRegisterCraftingRecipe() {
        init("register recipe for ", ItemStack.class);
        initLine(Material.class, ", ", Material.class, ", ", Material.class);
        initLine(Material.class, ", ", Material.class, ", ", Material.class);
        initLine(Material.class, ", ", Material.class, ", ", Material.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(REGISTER_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.registerRecipe(" + arg(0) + "," +
                arg(1) + "," + arg(2) + "," + arg(3) + "," +
                arg(4) + "," + arg(5) + "," + arg(6) + "," +
                arg(7) + "," + arg(8) + "," + arg(9) + ");";
    }

    private static final String REGISTER_METHOD =
            "private static void registerRecipe(ItemStack result, Material... ingredients) {\n" +
            "    ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(PluginMain.getInstance(), UUID.randomUUID().toString()), result);\n" +
            "    for (int i = 0; i < ingredients.length; i++) {\n" +
            "        Material ingredient = ingredients[i];\n" +
            "        if (ingredient != null && ingredient != Material.AIR) {\n" +
            "            if (i < 3) {\n" +
            "                recipe.shape(\"012\");\n" +
            "            } else if (i < 6) {\n" +
            "                recipe.shape(\"012\", \"345\");\n" +
            "            } else {\n" +
            "                recipe.shape(\"012\", \"345\", \"678\");\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "    for (int i = 0; i < ingredients.length; i++) {\n" +
            "        Material ingredient = ingredients[i];\n" +
            "        if (ingredient != null && ingredient != Material.AIR) {\n" +
            "            recipe.setIngredient(Character.forDigit(i, 10), ingredient);\n" +
            "        }\n" +
            "    }\n" +
            "    Bukkit.addRecipe(recipe);\n" +
            "}";
}
