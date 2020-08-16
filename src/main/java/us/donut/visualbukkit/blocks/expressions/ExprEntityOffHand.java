package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item in the off hand of a living entity", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprEntityOffHand extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("item in off hand of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getItemInOffHand()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".getEquipment().setItemInOffHand(" + delta + ");" : null;
    }
}
