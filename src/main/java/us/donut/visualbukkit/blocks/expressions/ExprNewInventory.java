package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"A new inventory", "Returns: inventory"})
public class ExprNewInventory extends ExpressionBlock<Inventory> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("new", InventoryType.class, "inventory named", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createInventory(null," + arg(0) + ",color(" + arg(1) + "))";
    }
}
