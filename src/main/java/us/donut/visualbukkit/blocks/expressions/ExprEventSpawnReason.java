package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.CreatureSpawnEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The spawn reason in a CreatureSpawnEvent", "Returns: spawn reason"})
@Event(CreatureSpawnEvent.class)
public class ExprEventSpawnReason extends ExpressionBlock<CreatureSpawnEvent.SpawnReason> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event spawn reason");
    }

    @Override
    public String toJava() {
        return "event.getSpawnReason()";
    }
}
