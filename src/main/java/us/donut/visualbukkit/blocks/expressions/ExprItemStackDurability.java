package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Category("Item Stack")
@Description({"The durability of an item stack", "Changers: set, add, remove", "Returns: number"})
public class ExprItemStackDurability extends ChangeableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("durability of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "((org.bukkit.inventory.meta.Damageable)" + arg(0) + ".getItemMeta()).getDamage()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        BuildContext.addUtilMethod("setDurability");
        switch (changeType) {
            case SET: return "UtilMethods.setDurability(" + arg(0) + "," + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
