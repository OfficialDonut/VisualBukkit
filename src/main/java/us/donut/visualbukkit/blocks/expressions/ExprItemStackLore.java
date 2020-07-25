package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.util.SimpleList;

@Category("Item Stack")
@Description({"The lore of an item stack", "Returns: list of strings"})
@Modifier(ModificationType.SET)
public class ExprItemStackLore extends ModifiableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("lore of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getItemMeta().getLore())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod("setItemLore");
        return modificationType == ModificationType.SET ? "UtilMethods.setItemLore(" + arg(0) + "," + delta + ");" : null;
    }
}
