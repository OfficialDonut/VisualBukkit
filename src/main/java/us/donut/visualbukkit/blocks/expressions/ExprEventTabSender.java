package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.command.CommandSender;
import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The sender in a TabCompleteEvent", "Returns: command sender"})
public class ExprEventTabSender extends ExpressionBlock<CommandSender> {

    @Override
    protected Syntax init() {
        return new Syntax("tab sender");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "event.getSender()";
    }
}
