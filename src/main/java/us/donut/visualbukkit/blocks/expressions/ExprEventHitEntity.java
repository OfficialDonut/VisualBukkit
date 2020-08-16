package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The entity that was hit in a ProjectileHitEvent", "Returns: entity"})
public class ExprEventHitEntity extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("hit entity");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(ProjectileHitEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getHitEntity()";
    }
}
