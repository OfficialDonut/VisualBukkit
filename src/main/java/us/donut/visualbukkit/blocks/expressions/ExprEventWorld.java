package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import org.bukkit.event.world.WorldEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The world involved in a WorldEvent", "Returns: world"})
public class ExprEventWorld extends ExpressionBlock<World> {

    @Override
    protected Syntax init() {
        return new Syntax("event world");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(WorldEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getWorld()";
    }
}
