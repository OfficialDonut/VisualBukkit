package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@Category(Category.ITEM)
@Description("Adds an enchantment to an item")
public class StatEnchantItem extends StatementBlock {

    public StatEnchantItem() {
        init("enchant item");
        initLine("item:        ", ItemStack.class);
        initLine("enchantment: ", Enchantment.class);
        initLine("level:       ", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".addUnsafeEnchantment(" + arg(1) + "," + arg(2) + ");";
    }
}
