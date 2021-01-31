package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.inventory.ItemStack;

@Description("Sets the unbreakable state of an item")
public class StatSetItemUnbreakableState extends StatementBlock {

    public StatSetItemUnbreakableState() {
        init("set unbreakable state of ", ItemStack.class, " to ", boolean.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(UNBREAKABLE_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setItemUnbreakable(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String UNBREAKABLE_METHOD =
            "public static void setItemUnbreakable(ItemStack item, boolean state) {\n" +
            "    ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setUnbreakable(state);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
