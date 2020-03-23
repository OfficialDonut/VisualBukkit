package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Entity")
@Description({"The entity involved in an event", "Returns: entity"})
@Event(EntityEvent.class)
public class ExprEventEntity extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event entity");
    }

    @Override
    public String toJava() {
        return "event.getEntity()";
    }

    @Override
    public Class<?> getReturnType() {
        return Entity.class;
    }
}
