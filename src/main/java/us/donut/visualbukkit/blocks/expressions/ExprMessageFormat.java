package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The message format in an AsyncPlayerChatEvent", "Returns: string"})
@Event(AsyncPlayerChatEvent.class)
public class ExprMessageFormat extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("message format");
    }

    @Override
    public String toJava() {
        return "event.getFormat()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? "event.setFormat(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
