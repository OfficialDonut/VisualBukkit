package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The contents of an inventory", "Returns: list of item stacks"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprInventoryContents extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("contents of", Inventory.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getContents())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case CLEAR: return arg(0) + ".clear();";
            case ADD: return arg(0) + ".addItem(new ItemStack[]{" + delta + "});";
            case REMOVE: return arg(0) + ".remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return ItemStack.class;
    }
}
