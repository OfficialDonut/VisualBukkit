package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The leggings of a living entity", "Changers: set", "Returns: item stack"})
public class ExprEntityLeggings extends ChangeableExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("leggings of", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getLeggings()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".getEquipment().setLeggings(" + delta + ");" : null;
    }
}
