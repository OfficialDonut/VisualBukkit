package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the durability damage of an item")
public class StatSetItemDurability extends StatementBlock {

    public StatSetItemDurability() {
        init("set durability damage of ", ItemStack.class, " to ", int.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(DURABILITY_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setDurability(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String DURABILITY_METHOD =
            "public static void setDurability(ItemStack item, int damage) {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta instanceof org.bukkit.inventory.meta.Damageable) {\n" +
            "        ((org.bukkit.inventory.meta.Damageable) itemMeta).setDamage(damage);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
