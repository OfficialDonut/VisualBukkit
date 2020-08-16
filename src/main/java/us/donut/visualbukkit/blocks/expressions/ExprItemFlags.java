package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The item flags of an item stack", "Returns: list of item flags"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE})
public class ExprItemFlags extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("item flags of", ItemStack.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getItemMeta().getItemFlags())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case ADD: return arg(0) + ".getItemMeta().addItemFlags(" + delta + ");";
            case REMOVE: return arg(0) + ".getItemMeta().removeItemFlags(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return ItemFlag.class;
    }
}
