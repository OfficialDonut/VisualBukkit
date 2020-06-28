package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The inventory of a container block", "Returns: inventory"})
public class ExprContainerInventory extends ExpressionBlock<Inventory> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("inventory of", Container.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory()";
    }
}
