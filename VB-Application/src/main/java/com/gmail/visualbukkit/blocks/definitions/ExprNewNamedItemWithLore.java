package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildContext;

public class ExprNewNamedItemWithLore extends Expression {

    public ExprNewNamedItemWithLore() {
        super("expr-new-named-item-with-lore");
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.Material")), new ExpressionParameter(ClassInfo.STRING), new ExpressionParameter(ClassInfo.LIST)) {
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
            "public static org.bukkit.inventory.ItemStack getNamedItemWithLore(Material material, String name, List<String> lore) {\n" +
            "    org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);\n" +
            "    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setDisplayName(name);\n" +
            "        itemMeta.setLore(lore);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "    return item;\n" +
            "}";
}
