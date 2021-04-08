package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.plugin.BuildContext;

public class StatSetItemName extends Statement {

    public StatSetItemName() {
        super("stat-set-item-name");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ExpressionParameter(ClassInfo.of("org.bukkit.inventory.ItemStack")), new ExpressionParameter(ClassInfo.STRING)) {
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
            "public static void setItemName(org.bukkit.inventory.ItemStack item, String name) {\n" +
            "    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();\n" +
            "    if (itemMeta != null) {\n" +
            "        itemMeta.setDisplayName(name);\n" +
            "        item.setItemMeta(itemMeta);\n" +
            "    }\n" +
            "}";
}
