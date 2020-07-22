package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.PlayerDeathEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The death message in a PlayerDeathEvent", "Returns: string"})
@Event(PlayerDeathEvent.class)
@Modifier(ModificationType.SET)
public class ExprDeathMessage extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("death message");
    }

    @Override
    public String toJava() {
        return "event.getDeathMessage()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? "event.setDeathMessage(PluginMain.color(" + delta + "));" : null;
    }
}
