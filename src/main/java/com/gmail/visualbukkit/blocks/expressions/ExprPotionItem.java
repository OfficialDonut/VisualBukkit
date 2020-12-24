package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

@Description("A potion item")
public class ExprPotionItem extends ExpressionBlock<ItemStack> {

    public ExprPotionItem() {
        init(PotionType.class, " potion item");
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(POTION_METHOD);
        context.getMainClass().addImport("org.bukkit.potion.*");
    }

    @Override
    public String toJava() {
        return "PluginMain.createPotion(" + arg(0) + ")";
    }

    private static final String POTION_METHOD =
            "public static ItemStack createPotion(PotionType potionType) {\n" +
            "    ItemStack item = new ItemStack(Material.POTION);\n" +
            "    PotionMeta potionMeta = (PotionMeta) item.getItemMeta();\n" +
            "    if (potionMeta != null) {\n" +
            "        potionMeta.setBasePotionData(new PotionData(potionType));\n" +
            "        item.setItemMeta(potionMeta);\n" +
            "    }\n" +
            "    return item;\n" +
            "}";
}