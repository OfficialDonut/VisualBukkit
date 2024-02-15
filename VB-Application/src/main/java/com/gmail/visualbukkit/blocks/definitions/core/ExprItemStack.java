package com.gmail.visualbukkit.blocks.definitions.core;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.util.List;

@BlockDefinition(id = "expr-itemstack", name = "ItemStack", description = "An ItemStack optionally with a name and lore")
public class ExprItemStack extends ExpressionBlock {

    public ExprItemStack() {
        addParameter("Material", new ExpressionParameter(ClassInfo.of("org.bukkit.Material")));
        addParameter("Name", new ExpressionParameter(ClassInfo.of(String.class)));
        addParameter("Lore", new ExpressionParameter(ClassInfo.of(List.class)));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        if (buildInfo.getMetadata().putIfAbsent("newItemStack()", true) == null) {
            buildInfo.getMainClass().addMethod(
                    """
                    public static org.bukkit.inventory.ItemStack newItemStack(Material material, String name, List lore) {
                         org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material);
                         org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                         if (itemMeta != null) {
                             if (name != null) { itemMeta.setDisplayName(name); }
                             if (lore != null) { itemMeta.setLore(lore); }
                             item.setItemMeta(itemMeta);
                         }
                         return item;
                     }
                    """);
        }
        return "PluginMain.newItemStack(" + arg(0, buildInfo) + "," + arg(1, buildInfo) + "," + arg(2, buildInfo) + ")";
    }
}
