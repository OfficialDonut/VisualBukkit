package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerTeleportEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The teleport cause in a PlayerTeleportEvent", "Returns: teleport cause"})
public class ExprEventTeleportCause extends ExpressionBlock<PlayerTeleportEvent.TeleportCause> {

    @Override
    protected Syntax init() {
        return new Syntax("event teleport cause");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerTeleportEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getCause()";
    }
}
