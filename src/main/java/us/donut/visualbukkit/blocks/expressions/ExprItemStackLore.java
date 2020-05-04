package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Item Stack")
@Description({"The lore of an item stack", "Changers: set", "Returns: list of strings"})
public class ExprItemStackLore extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("lore of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getItemMeta().getLore()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        if (changeType == ChangeType.SET) {
            String itemStackVar = randomVar();
            String itemMetaVar = randomVar();
            return "ItemStack " + itemStackVar + "=" + arg(0) + ";" +
                    "ItemMeta " + itemMetaVar + "=" + itemStackVar + ".getItemMeta();" +
                    itemMetaVar + ".setLore(PluginMain.color(" + delta + "));" +
                    itemStackVar + ".setItemMeta(" + itemMetaVar + ");";
        }
        return null;
    }
}
