package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerTeleportEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Description({"The teleport cause in a PlayerTeleportEvent", "Returns: teleport cause"})
@Event(PlayerTeleportEvent.class)
public class ExprEventTeleportCause extends ExpressionBlock<PlayerTeleportEvent.TeleportCause> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event teleport cause");
    }

    @Override
    public String toJava() {
        if (PlayerTeleportEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            return "event.getCause()";
        }
        throw new IllegalStateException();
    }
}
