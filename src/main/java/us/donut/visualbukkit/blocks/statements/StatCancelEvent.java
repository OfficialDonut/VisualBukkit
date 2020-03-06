package us.donut.visualbukkit.blocks.statements;

import org.bukkit.event.Cancellable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Cancels an event")
@Event(Cancellable.class)
public class StatCancelEvent extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("cancel event");
    }

    @Override
    public String toJava() {
        return "event.setCancelled(true);";
    }
}
