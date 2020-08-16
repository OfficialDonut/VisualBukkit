package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The first empty slot in an inventory (-1 if full)", "Returns: number"})
public class ExprFirstEmptySlot extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("first empty slot in", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".firstEmpty()";
    }
}
