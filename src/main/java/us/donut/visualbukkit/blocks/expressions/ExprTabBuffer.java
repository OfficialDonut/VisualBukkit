package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The buffer in a TabCompleteEvent", "Returns: string"})
@Event(TabCompleteEvent.class)
public class ExprTabBuffer extends ExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("tab buffer");
    }

    @Override
    public String toJava() {
        return "event.getBuffer()";
    }
}
