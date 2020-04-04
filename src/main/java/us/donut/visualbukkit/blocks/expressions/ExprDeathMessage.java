package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.PlayerDeathEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The death message in a PlayerDeathEvent", "Returns: string"})
@Event(PlayerDeathEvent.class)
public class ExprDeathMessage extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("death message");
    }

    @Override
    public String toJava() {
        return "event.getDeathMessage()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? "event.setDeathMessage(" + delta + ");" : null;
    }
}
