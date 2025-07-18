package com.gmail.visualbukkit.blocks.definitions.gui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassInfo;

import java.net.URI;

@BlockDefinition(id = "expr-gui-clicked-itemstack", name = "GUI Clicked ItemStack", description = "Must be used in a 'GUI Click Handler' plugin component")
public class ExprGUIClickedItemStack extends ExpressionBlock {

    @Override
    public void updateState() {
        super.updateState();
        checkForPluginComponent(CompGUIClickHandler.class);
    }

    @Override
    public void openJavadocs() {
        VisualBukkitApp.openURI(URI.create("https://jd.papermc.io/paper/1.21.4/org/bukkit/event/inventory/InventoryClickEvent.html#getCurrentItem()"));
    }

    @Override
    public ClassInfo getReturnType() {
        return ClassInfo.of("org.bukkit.inventory.ItemStack");
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return "guiClickEvent.getInventoryClickEvent().getCurrentItem()";
    }
}
