package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetItemLore extends Statement {

    public StatSetItemLore() {
        super("stat-set-item-lore", "Set ItemStack Lore", "ItemStack", "Sets the lore of an ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("ItemStack", ClassInfo.of("org.bukkit.inventory.ItemStack")), new ExpressionParameter("Lore", ClassInfo.LIST)) {
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
            """
            public static void setItemLore(org.bukkit.inventory.ItemStack item, List lore) {
                org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setLore((List<String>) lore);
                    item.setItemMeta(itemMeta);
                }
            }
            """;
}
