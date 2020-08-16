package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.UtilMethod;

import java.util.List;

@Description({"The lore of an item stack", "Returns: list of strings"})
@Modifier(ModificationType.SET)
public class ExprItemStackLore extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("lore of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getItemMeta().getLore())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        BuildContext.addUtilMethod(UtilMethod.SET_ITEM_LORE);
        return modificationType == ModificationType.SET ? "UtilMethods.setItemLore(" + arg(0) + "," + delta + ");" : null;
    }
}
