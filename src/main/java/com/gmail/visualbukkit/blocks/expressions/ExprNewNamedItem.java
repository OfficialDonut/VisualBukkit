package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Description("A named item")
public class ExprNewNamedItem extends ExpressionBlock<ItemStack> {

    public ExprNewNamedItem() {
        init("item of ", Material.class, " named ", String.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(ITEM_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.getNamedItem(" + arg(0) + "," + arg(1) + ")";
    }

    private static final String ITEM_METHOD =
            "public static ItemStack getNamedItem(Material material, String name) {\n" +
            "    ItemStack item = new ItemStack(material);\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setDisplayName(PluginMain.color(name));\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "    return item;\n" +
            "}";
}
