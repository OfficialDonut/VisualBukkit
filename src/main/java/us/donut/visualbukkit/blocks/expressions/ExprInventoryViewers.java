package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Inventory")
@Description({"The viewers of an inventory", "Returns: list of human entities"})
public class ExprInventoryViewers extends ExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("viewers of", Inventory.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getViewers())";
    }
}
