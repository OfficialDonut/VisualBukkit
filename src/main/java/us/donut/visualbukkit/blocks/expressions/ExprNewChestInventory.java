package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"A new inventory", "Returns: inventory"})
public class ExprNewChestInventory extends ExpressionBlock<Inventory> {

    @Override
    protected Syntax init() {
        return new Syntax("new chest inventory named", String.class, "with size", int.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createInventory(null," + arg(1) + ",PluginMain.color(" + arg(0) + "))";
    }
}
