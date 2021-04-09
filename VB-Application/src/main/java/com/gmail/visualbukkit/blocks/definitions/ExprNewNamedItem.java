package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Expression;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class ExprNewNamedItem extends Expression {

    public ExprNewNamedItem() {
        super("expr-new-named-item", ClassInfo.of("org.bukkit.inventory.ItemStack"));
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.Material")), new ExpressionParameter(ClassInfo.STRING)) {
            @Override
            public void prepareBuild(BuildContext buildContext) {
                super.prepareBuild(buildContext);
                buildContext.addUtilMethod(ITEM_METHOD);
            }

            @Override
            public String toJava() {
                return "PluginMain.getNamedItem(" + arg(0) + "," + arg(1) + ")";
            }
        };
    }

    private static final String ITEM_METHOD =
            "public static org.bukkit.inventory.ItemStack getNamedItem(Material material, String name) {\n" +
            "    org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);\n" +
            "    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setDisplayName(name);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "    return item;\n" +
            "}";
}
