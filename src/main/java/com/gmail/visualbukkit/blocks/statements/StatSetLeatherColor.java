package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

@Description("Sets the color of a leather armor item")
public class StatSetLeatherColor extends StatementBlock {

    public StatSetLeatherColor() {
        init("set leather color of ", ItemStack.class, " to ", Color.class);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.addUtilMethods(LEATHER_METHOD);
    }

    @Override
    public String toJava() {
        return "PluginMain.setLeatherColor(" + arg(0) + "," + arg(1) + ");";
    }

    private static final String LEATHER_METHOD =
            "public static void setLeatherColor(ItemStack item, Color color) {\n" +
            "    org.bukkit.inventory.meta.LeatherArmorMeta itemMeta = (org.bukkit.inventory.meta.LeatherArmorMeta) item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setColor(color);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
