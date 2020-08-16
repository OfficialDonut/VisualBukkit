package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The block/entity to which an inventory belongs", "Returns: inventory holder"})
public class ExprInventoryHolder extends ExpressionBlock<InventoryHolder> {

    @Override
    protected Syntax init() {
        return new Syntax("holder of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHolder()";
    }
}
