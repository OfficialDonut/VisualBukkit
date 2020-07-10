package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;

@Category("Item Stack")
@Description({"The name of an item stack", "Changers: set", "Returns: string"})
public class ExprItemStackName extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("name of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getDisplayName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        BuildContext.addUtilMethod("setItemName");
        return changeType == ChangeType.SET ? "UtilMethods.setItemName(" + arg(0) + "," + delta + ");" : null;
    }
}
