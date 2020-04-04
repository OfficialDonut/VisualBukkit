package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The damager in an EntityDamageByEntityEvent", "Returns: entity"})
@Event(EntityDamageByEntityEvent.class)
public class ExprDamager extends ExpressionBlock<Entity> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("damager");
    }

    @Override
    public String toJava() {
        return "event.getDamager()";
    }
}
