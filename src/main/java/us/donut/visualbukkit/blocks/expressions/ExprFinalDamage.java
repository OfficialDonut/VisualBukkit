package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.EntityDamageEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The final damage amount in an EntityDamageEvent", "Returns: number"})
@Event(EntityDamageEvent.class)
public class ExprFinalDamage extends ExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("final damage");
    }

    @Override
    public String toJava() {
        return "event.getFinalDamage()";
    }
}
