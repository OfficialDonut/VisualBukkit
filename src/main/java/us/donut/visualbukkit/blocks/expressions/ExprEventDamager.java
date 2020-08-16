package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The damager in an EntityDamageByEntityEvent", "Returns: entity"})
public class ExprEventDamager extends ExpressionBlock<Entity> {

    @Override
    protected Syntax init() {
        return new Syntax("damager");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityDamageByEntityEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getDamager()";
    }
}
