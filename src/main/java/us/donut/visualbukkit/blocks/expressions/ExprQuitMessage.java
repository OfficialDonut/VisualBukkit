package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerQuitEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The quit message in a PlayerQuitEvent", "Returns: string"})
@Event(PlayerQuitEvent.class)
@Modifier(ModificationType.SET)
public class ExprQuitMessage extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("quit message");
    }

    @Override
    public String toJava() {
        return "event.getQuitMessage()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? "event.setQuitMessage(PluginMain.color(" + delta + "));" : null;
    }
}
