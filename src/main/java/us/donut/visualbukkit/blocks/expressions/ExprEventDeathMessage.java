package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.entity.PlayerDeathEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The death message in a PlayerDeathEvent", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprEventDeathMessage extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("death message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerDeathEvent.class);
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
