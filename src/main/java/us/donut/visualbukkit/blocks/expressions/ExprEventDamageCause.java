package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDamageEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The damage cause in an EntityDamageEvent", "Returns: damage cause"})
public class ExprEventDamageCause extends ExpressionBlock<EntityDamageEvent.DamageCause> {

    @Override
    protected Syntax init() {
        return new Syntax("event damage cause");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCause()";
    }
}
