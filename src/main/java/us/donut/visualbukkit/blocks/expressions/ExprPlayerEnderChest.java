package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The ender chest inventory of a player", "Returns: inventory"})
public class ExprPlayerEnderChest extends ExpressionBlock<Inventory> {

    @Override
    protected Syntax init() {
        return new Syntax("ender chest of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEnderChest()";
    }
}
