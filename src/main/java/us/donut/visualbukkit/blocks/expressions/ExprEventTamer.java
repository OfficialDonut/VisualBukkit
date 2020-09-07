package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.entity.EntityTameEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The tamer in an EntityTameEvent", "Returns: animal tamer"})
public class ExprEventTamer extends ExpressionBlock<AnimalTamer> {

    @Override
    protected Syntax init() {
        return new Syntax("event tamer");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(EntityTameEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getOwner()";
    }
}
