package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Description("Register shapeless recipe")
public class StatRegisterShapelessRecipe extends StatementBlock {

    public StatRegisterShapelessRecipe() {
        init("register shapeless recipe for ", ItemStack.class, " with ingredients ", List.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(REGISTER_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.registerShapelessRecipe(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String REGISTER_METHOD =
            "public static void registerShapelessRecipe(ItemStack result, List materials) {\n" +
            "    ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(PluginMain.getInstance(), UUID.randomUUID().toString()), result);\n" +
            "    for (Object material : materials) {\n" +
            "        if (material instanceof Material) {\n" +
            "            recipe.addIngredient((Material) material);\n" +
            "        }\n" +
            "    }\n" +
            "    Bukkit.addRecipe(recipe);\n" +
            "}";
}
