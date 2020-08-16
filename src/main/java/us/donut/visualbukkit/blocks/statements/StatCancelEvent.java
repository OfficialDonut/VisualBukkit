package us.donut.visualbukkit.blocks.statements;

import org.bukkit.event.Cancellable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Cancels an event")
public class StatCancelEvent extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("cancel event");
    }

    @Override
    public void validate() throws IllegalStateException {
        super.validate();
        validateEvent(Cancellable.class);
    }

    @Override
    public String toJava() {
        return "event.setCancelled(true);";
    }
}
