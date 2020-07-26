package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Category("Item Stack")
@Description({"The item flags of an item stack", "Returns: list of item flags"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE})
public class ExprItemFlags extends ModifiableExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("item flags of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getItemMeta().getItemFlags())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case ADD: return arg(0) + ".getItemMeta().addItemFlags(new ItemFlag[]{" + delta + "});";
            case REMOVE: return arg(0) + ".getItemMeta().removeItemFlags(new ItemFlag[]{" + delta + "});";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return ItemFlag.class;
    }
}
