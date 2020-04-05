package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ProjectileHitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The entity that was hit in a ProjectileHitEvent", "Returns: entity"})
@Event(ProjectileHitEvent.class)
public class ExprHitEntity extends ExpressionBlock<Entity> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("hit entity");
    }

    @Override
    public String toJava() {
        return "event.getHitEntity()";
    }
}
