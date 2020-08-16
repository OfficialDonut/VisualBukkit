package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The resource pack status in a PlayerResourcePackStatusEvent", "Returns: status"})
public class ExprEventResourcePackStatus extends ExpressionBlock<PlayerResourcePackStatusEvent.Status> {

    @Override
    protected Syntax init() {
        return new Syntax("resource pack status");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerResourcePackStatusEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getStatus()";
    }
}
