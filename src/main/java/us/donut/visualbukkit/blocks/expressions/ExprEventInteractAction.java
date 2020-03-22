package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.editor.EventPane;

@Description({"The interact action in a PlayerInteractEvent", "Returns: action"})
@Event(PlayerInteractEvent.class)
public class ExprEventInteractAction extends ExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("event interact action");
    }

    @Override
    public String toJava() {
        if (PlayerInteractEvent.class.isAssignableFrom(((EventPane) getBlockPane()).getEvent())) {
            return "event.getAction()";
        }
        throw new IllegalStateException();
    }

    @Override
    public Class<?> getReturnType() {
        return Action.class;
    }
}
