package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.CreatureSpawnEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The spawn reason in a CreatureSpawnEvent", "Returns: spawn reason"})
public class ExprEventSpawnReason extends ExpressionBlock<CreatureSpawnEvent.SpawnReason> {

    @Override
    protected Syntax init() {
        return new Syntax("event spawn reason");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(CreatureSpawnEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSpawnReason()";
    }
}
