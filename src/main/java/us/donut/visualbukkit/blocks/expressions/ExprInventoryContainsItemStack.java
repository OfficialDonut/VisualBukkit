package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if an inventory contains an item stack", "Returns: boolean"})
public class ExprInventoryContainsItemStack extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(Inventory.class, new ChoiceParameter("contains", "does not contain"), "at least", int.class, ItemStack.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".containsAtLeast(" + arg(3) + "," + arg(2) + ")";
    }
}
