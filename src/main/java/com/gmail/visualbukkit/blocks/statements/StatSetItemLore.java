package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Category(Category.ITEM)
@Description("Sets the lore of an item")
public class StatSetItemLore extends StatementBlock {

    public StatSetItemLore() {
        init("set lore of ", ItemStack.class, " to ", List.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(LORE_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setItemLore(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String LORE_METHOD =
            "public static void setItemLore(ItemStack item, List lore) {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        List<String> coloredLore = new ArrayList<>(lore.size());\n" +
            "        for (Object obj : lore) {\n" +
            "            coloredLore.add(PluginMain.color((String) obj));\n" +
            "        }\n" +
            "        itemMeta.setLore(coloredLore);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
