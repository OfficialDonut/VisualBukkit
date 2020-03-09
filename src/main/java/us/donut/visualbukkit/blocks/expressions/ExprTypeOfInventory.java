package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description("The size of an inventory")
public class ExprTypeOfInventory extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("type of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getType()";
    }

    @Override
    public Class<?> getReturnType() {
        return InventoryType.class;
    }
}
