package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item stack in a slot of an inventory", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprInventorySlot extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("slot", int.class, "of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getItem(" + arg(0) + ")";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(1) + ".setItem(" + arg(0) + "," + delta + ");" : null;
    }
}
