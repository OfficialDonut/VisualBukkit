package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The player involved in an PlayerEvent", "Returns: player"})
public class ExprEventPlayer extends ExpressionBlock<Player> {

    @Override
    protected Syntax init() {
        return new Syntax("event player");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getPlayer()";
    }
}
