package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Item Stack")
@Description({"The name of an item stack", "Returns: string"})
public class ExprItemStackName extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("name of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getName()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        if (changeType == ChangeType.SET) {
            String itemStackVar = randomVar();
            String itemMetaVar = randomVar();
            return "ItemStack " + itemStackVar + "=" + arg(0) + ";" +
                    "ItemMeta " + itemMetaVar + "=" + itemStackVar + ".getItemMeta();" +
                    itemMetaVar + ".setDisplayName(color(" + delta + "));" +
                    itemStackVar + ".setItemMeta(" + itemMetaVar + ");";
        }
        return null;
    }
}
