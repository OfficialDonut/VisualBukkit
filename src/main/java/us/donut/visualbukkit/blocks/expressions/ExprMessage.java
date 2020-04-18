package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The message in an AsyncPlayerChatEvent", "Changers: set", "Returns: string"})
@Event(AsyncPlayerChatEvent.class)
public class ExprMessage extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("message");
    }

    @Override
    public String toJava() {
        return "event.getMessage()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? "event.setMessage(" + delta + ");" : null;
    }
}
