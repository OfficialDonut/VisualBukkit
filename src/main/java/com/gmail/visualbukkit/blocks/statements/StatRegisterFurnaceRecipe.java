package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Description("Registers a furnace recipe")
public class StatRegisterFurnaceRecipe extends StatementBlock {

    public StatRegisterFurnaceRecipe() {
        init("register furnace recipe");
        initLine("input:      ", Material.class);
        initLine("output:     ", ItemStack.class);
        initLine("experience: ", float.class);
        initLine("cook time:  ", int.class, " (ticks)");
    }

    @Override
    public String toJava() {
        return "Bukkit.addRecipe(new FurnaceRecipe(" +
                "new NamespacedKey(PluginMain.getInstance(), UUID.randomUUID().toString())," +
                arg(1) + "," + arg(0) + ","  + arg(2) + "," + arg(3) + "));";
    }
}
