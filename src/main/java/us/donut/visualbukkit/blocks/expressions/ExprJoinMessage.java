package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerJoinEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The join message in a PlayerJoinEvent", "Returns: string"})
@Event(PlayerJoinEvent.class)
public class ExprJoinMessage extends ChangeableExpressionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("join message");
    }

    @Override
    public String toJava() {
        return "event.getJoinMessage()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? "event.setJoinMessage(" + delta + ");" : null;
    }

    @Override
    public Class<?> getReturnType() {
        return String.class;
    }
}
