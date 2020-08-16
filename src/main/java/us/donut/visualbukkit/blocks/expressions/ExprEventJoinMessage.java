package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerJoinEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The join message in a PlayerJoinEvent", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprEventJoinMessage extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("join message");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerJoinEvent.class);
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
