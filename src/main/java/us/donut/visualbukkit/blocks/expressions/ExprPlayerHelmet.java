package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The item stack in the helmet slot of a player", "Returns: item stack"})
@Modifier(ModificationType.SET)
public class ExprPlayerHelmet extends ExpressionBlock<ItemStack> {

    @Override
    protected Syntax init() {
        return new Syntax("helmet of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getInventory().getHelmet()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".getInventory().setHelmet(" + delta + ");" : null;
    }
}
