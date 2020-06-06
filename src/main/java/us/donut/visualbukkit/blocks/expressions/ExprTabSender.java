package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.command.CommandSender;
import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The sender in a TabCompleteEvent", "Returns: command sender"})
@Event(TabCompleteEvent.class)
public class ExprTabSender extends ExpressionBlock<CommandSender> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("tab sender");
    }

    @Override
    public String toJava() {
        return "event.getSender()";
    }
}
