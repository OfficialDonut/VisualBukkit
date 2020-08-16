package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item in the main hand of a living entity", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprEntityMainHand extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("item in main hand of", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getItemInMainHand()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".getEquipment().setItemInMainHand(" + delta + ");" : null;
    }
}
