package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The helmet of a living entity", "Changers: set", "Returns: item stack"})
public class ExprEntityHelmet extends ChangeableExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("helmet of", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getHelmet()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".getEquipment().setHelmet(" + delta + ");" : null;
    }
}
