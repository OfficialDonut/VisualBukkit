package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Category("Inventory")
@Description({"The contents of an inventory", "Returns: list of item stacks"})
public class ExprInventoryContents extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("contents of", Inventory.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getContents())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case CLEAR: return arg(0) + ".clear();";
            case ADD: return arg(0) + ".addItem(new ItemStack[]{" + delta + "});";
            case REMOVE: return arg(0) + ".remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return ItemStack.class;
    }
}
