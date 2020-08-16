package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item in the main hand of a player", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprPlayerMainHand extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("item in main hand of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getItemInMainHand()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".getInventory().setItemInMainHand(" + delta + ");" : null;
    }
}
