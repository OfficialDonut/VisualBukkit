package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDeathEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The amount of experience dropped in an EntityDeathEvent", "Returns: number"})
@Event(EntityDeathEvent.class)
public class ExprDroppedExperience extends ChangeableExpressionBlock<Integer> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("dropped experience");
    }

    @Override
    public String toJava() {
        return "event.getDroppedExp()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "event.setDroppedExp(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
