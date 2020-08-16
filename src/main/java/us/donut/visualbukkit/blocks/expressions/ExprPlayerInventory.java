package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.PlayerInventory;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The inventory of a player", "Returns: player inventory"})
public class ExprPlayerInventory extends ExpressionBlock<PlayerInventory> {

    @Override
    protected Syntax init() {
        return new Syntax("inventory of", HumanEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory()";
    }
}
