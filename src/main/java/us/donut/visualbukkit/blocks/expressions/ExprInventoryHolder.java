package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The block/entity to which an inventory belongs", "Returns: inventory holder"})
public class ExprInventoryHolder extends ExpressionBlock<InventoryHolder> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("holder of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getHolder()";
    }
}
