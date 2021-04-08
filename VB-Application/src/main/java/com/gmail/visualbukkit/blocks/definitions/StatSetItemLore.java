package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatSetItemLore extends Statement {

    public StatSetItemLore() {
        super("stat-set-item-lore");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.inventory.ItemStack")), new ExpressionParameter(ClassInfo.LIST)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(LORE_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.setItemLore(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }

    private static final String LORE_METHOD =
            "public static void setItemLore(org.bukkit.inventory.ItemStack item, List lore) {\n" +
            "    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setLore((List<String>) lore);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
