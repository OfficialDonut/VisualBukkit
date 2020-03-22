package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDamageEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The damage cause in an EntityDamageEvent", "Returns: damage cause"})
@Event(EntityDamageEvent.class)
public class ExprEventDamageCause extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event damage cause");
    }

    @Override
    public String toJava() {
        return "event.getCause()";
    }

    @Override
    public Class<?> getReturnType() {
        return EntityDamageEvent.DamageCause.class;
    }
}
