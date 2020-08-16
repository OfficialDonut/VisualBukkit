package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The interact action in a PlayerInteractEvent", "Returns: action"})
public class ExprEventInteractAction extends ExpressionBlock<Action> {

    @Override
    protected Syntax init() {
        return new Syntax("event interact action");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerInteractEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getAction()";
    }
}
