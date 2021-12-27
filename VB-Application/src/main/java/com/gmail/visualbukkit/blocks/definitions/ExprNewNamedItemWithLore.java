package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprNewNamedItemWithLore extends Expression {

    public ExprNewNamedItemWithLore() {
        super("expr-new-named-item-with-lore", "New Named ItemStack With Lore", "ItemStack", "A named ItemStack with lore");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter("Material", ClassInfo.of("org.bukkit.Material")), new ExpressionParameter("Name", ClassInfo.STRING), new ExpressionParameter("Lore", ClassInfo.LIST)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(ITEM_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.getNamedItemWithLore(" + arg(0) + "," + arg(1) + "," + arg(2) + ")";
            }
        };
    }

    private static final String ITEM_METHOD =
            """
            public static org.bukkit.inventory.ItemStack getNamedItemWithLore(Material material, String name, List<String> lore) {
                org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);
                org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setDisplayName(name);
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                }
                return item;
            }
            """;
}
