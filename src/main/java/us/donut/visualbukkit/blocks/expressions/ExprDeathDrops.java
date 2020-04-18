package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The item stacks dropped in an EntityDeathEvent", "Changers: clear, add, remove", "Returns: list of item stacks"})
@Event(EntityDeathEvent.class)
public class ExprDeathDrops extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("death drops");
    }

    @Override
    public String toJava() {
        return "new SimpleList(event.getDrops())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case CLEAR: return "event.getDrops().clear();";
            case ADD: return "event.getDrops().add(" + delta + ");";
            case REMOVE: return "event.getDrops().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return ItemStack.class;
    }
}
