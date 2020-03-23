package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The item stack in the leggings slot of a player", "Returns: item stack"})
public class ExprPlayerLeggings extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("leggings of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getLeggings()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".getInventory().setLeggings(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return ItemStack.class;
    }
}
