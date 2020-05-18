package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerQuitEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The quit message in a PlayerQuitEvent", "Changers: set", "Returns: string"})
@Event(PlayerQuitEvent.class)
public class ExprQuitMessage extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("quit message");
    }

    @Override
    public String toJava() {
        return "event.getQuitMessage()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? "event.setQuitMessage(PluginMain.color(" + delta + "));" : null;
    }
}
