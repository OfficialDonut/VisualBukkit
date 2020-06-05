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
import us.donut.visualbukkit.util.SimpleList;

import java.util.ArrayList;
import java.util.List;

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
        return changeType == ChangeType.SET ? "setItemLore(" + arg(0) + "," + delta + ");" : null;
    }

    @UtilMethod
    public static void setItemLore(ItemStack item, SimpleList lore) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            List<String> coloredLore = new ArrayList<>(lore.size());
            for (Object obj : lore) {
                coloredLore.add(PluginMain.color((String) obj));
            }
            itemMeta.setLore(coloredLore);
            item.setItemMeta(itemMeta);
        }
    }
}
