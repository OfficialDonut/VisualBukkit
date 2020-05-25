package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The item in the off hand of a living entity", "Changers: set", "Returns: item stack"})
public class ExprEntityOffHand extends ChangeableExpressionBlock<ItemStack> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("item in off hand of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getEquipment().getItemInOffHand()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".getEquipment().setItemInOffHand(" + delta + ");" : null;
    }
}
