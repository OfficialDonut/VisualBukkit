package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The entity involved in an EntityEvent", "Returns: entity"})
public class ExprEventEntity extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("event entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getEntity()";
    }
}
