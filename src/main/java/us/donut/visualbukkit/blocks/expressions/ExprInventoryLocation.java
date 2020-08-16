package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The location of an inventory", "Returns: location"})
public class ExprInventoryLocation extends ExpressionBlock<Location> {

    @Override
    protected Syntax init() {
        return new Syntax("location of", Inventory.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getLocation()";
    }
}
