package us.donut.visualbukkit.blocks.statements;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Adds an enchantment to an item stack")
public class StatEnchantItemStack extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("enchant", ItemStack.class, "with", Enchantment.class, "level", int.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".addUnsafeEnchantment(" + arg(1) + "," + arg(2) + ");";
    }
}
