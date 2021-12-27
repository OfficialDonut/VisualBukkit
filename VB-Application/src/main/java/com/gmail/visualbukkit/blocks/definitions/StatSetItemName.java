package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class StatSetItemName extends Statement {

    public StatSetItemName() {
        super("stat-set-item-name", "Set ItemStack Name", "ItemStack", "Sets the name of an ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("ItemStack", ClassInfo.of("org.bukkit.inventory.ItemStack")), new ExpressionParameter("Name", ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(NAME_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.setItemName(" + arg(0) + "," + arg(1) + ");";
            }
        };
    }

    private static final String NAME_METHOD =
            """
            public static void setItemName(org.bukkit.inventory.ItemStack item, String name) {
                org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setDisplayName(name);
                    item.setItemMeta(itemMeta);
                }
            }
            """;
}
