package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDamageEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The final damage amount in an EntityDamageEvent", "Returns: number"})
public class ExprEventFinalDamage extends ExpressionBlock<Double> {

    @Override
    protected Syntax init() {
        return new Syntax("final damage");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityDamageEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getFinalDamage()";
    }
}
