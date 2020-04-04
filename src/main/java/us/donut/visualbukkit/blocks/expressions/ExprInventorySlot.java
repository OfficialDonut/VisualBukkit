package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description("The item stack in a slot of an inventory")
public class ExprInventorySlot extends ChangeableExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("slot", int.class, "of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".getItem(" + arg(0) + ")";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(1) + ".setItem(" + arg(0) + "," + delta + ");" : null;
    }
}
