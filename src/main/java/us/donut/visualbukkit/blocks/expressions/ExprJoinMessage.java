package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerJoinEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The join message in a PlayerJoinEvent", "Returns: string"})
@Event(PlayerJoinEvent.class)
@Modifier(ModificationType.SET)
public class ExprJoinMessage extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("join message");
    }

    @Override
    public String toJava() {
        return "event.getJoinMessage()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? "event.setJoinMessage(PluginMain.color(" + delta + "));" : null;
    }
}
