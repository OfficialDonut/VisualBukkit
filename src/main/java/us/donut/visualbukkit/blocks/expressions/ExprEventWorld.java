package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.event.world.WorldEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The world involved in an event", "Returns: world"})
@Event(WorldEvent.class)
public class ExprEventWorld extends ExpressionBlock<World> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event world");
    }

    @Override
    public String toJava() {
        return "event.getWorld()";
    }
}
