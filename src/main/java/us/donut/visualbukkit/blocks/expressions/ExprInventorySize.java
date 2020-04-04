package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description("The size of an inventory")
public class ExprInventorySize extends ExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("size of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSize()";
    }
}
