package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The inventory involved in an event", "Returns: inventory"})
public class ExprEventInventory extends ExpressionBlock<Inventory> {

    @Override
    protected Syntax init() {
        return new Syntax("event inventory");
    }

    @Override
    public String toJava() {
        return "event.getInventory()";
    }
}
