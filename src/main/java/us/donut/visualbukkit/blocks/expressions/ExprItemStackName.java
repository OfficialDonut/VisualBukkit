package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.UtilMethod;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.PluginMain;

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
        return changeType == ChangeType.SET ? "setItemName(" + arg(0) + "," + delta + ");" : null;
    }

    @UtilMethod
    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(PluginMain.color(name));
        }
    }
}
