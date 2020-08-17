package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The killed player in a PlayerDeathEvent", "Returns: player"})
public class ExprEventKilledPlayer extends ExpressionBlock<Player> {

    @Override
    protected Syntax init() {
        return new Syntax("killed player");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerDeathEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getEntity()";
    }
}
