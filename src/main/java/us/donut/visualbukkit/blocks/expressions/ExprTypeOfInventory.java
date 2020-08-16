package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("The size of an inventory")
public class ExprTypeOfInventory extends ExpressionBlock<InventoryType> {

    @Override
    protected Syntax init() {
        return new Syntax("type of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }
}
