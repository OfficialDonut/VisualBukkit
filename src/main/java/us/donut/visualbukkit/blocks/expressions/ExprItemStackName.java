package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

@Description({"The name of an item stack", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprItemStackName extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("name of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getDisplayName()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod(UtilMethod.SET_ITEM_NAME);
        return modificationType == ModificationType.SET ? "UtilMethods.setItemName(" + arg(0) + "," + delta + ");" : null;
    }
}
