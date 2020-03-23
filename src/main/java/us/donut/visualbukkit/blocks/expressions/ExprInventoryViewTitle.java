package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.InventoryView;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Inventory")
@Description({"The title of an inventory view", "Returns: string"})
public class ExprInventoryViewTitle extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("title of", InventoryView.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
