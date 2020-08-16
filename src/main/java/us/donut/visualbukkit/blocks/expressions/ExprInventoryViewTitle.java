package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.InventoryView;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The title of an inventory view", "Returns: string"})
public class ExprInventoryViewTitle extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("title of", InventoryView.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }
}
