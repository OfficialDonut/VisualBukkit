package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"The durability damage of an item stack", "Returns: number"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprItemStackDurability extends ExpressionBlock<Integer> {

    @Override
    protected Syntax init() {
        return new Syntax("durability damage of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((org.bukkit.inventory.meta.Damageable)" + arg(0) + ".getItemMeta()).getDamage()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod(UtilMethod.SET_DURABILITY);
        switch (modificationType) {
            case SET: return "UtilMethods.setDurability(" + arg(0) + "," + delta + ");";
            case ADD: return modify(ModificationType.SET, toJava() + "+" + delta);
            case REMOVE: return modify(ModificationType.SET, toJava() + "-" + delta);
            default: return null;
        }
    }
}
