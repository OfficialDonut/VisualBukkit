package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerQuitEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The quit message in a PlayerQuitEvent", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprEventQuitMessage extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("quit message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerQuitEvent.class);
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
