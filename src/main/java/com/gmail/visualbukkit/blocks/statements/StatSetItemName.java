package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Sets the name of an item")
public class StatSetItemName extends StatementBlock {

    public StatSetItemName() {
        init("set name of ", ItemStack.class, " to ", String.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(NAME_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setItemName(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String NAME_METHOD =
            "public static void setItemName(ItemStack item, String name) {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setDisplayName(PluginMain.color(name));\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
