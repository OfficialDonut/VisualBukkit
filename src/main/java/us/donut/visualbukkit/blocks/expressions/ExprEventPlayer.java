package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Category;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Category("Player")
@Description({"The player involved in an event", "Returns: player"})
@Event(PlayerEvent.class)
public class ExprEventPlayer extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event player");
    }

    @Override
    public String toJava() {
        return "event.getPlayer()";
    }

    @Override
    public Class<?> getReturnType() {
        return Player.class;
    }
}
