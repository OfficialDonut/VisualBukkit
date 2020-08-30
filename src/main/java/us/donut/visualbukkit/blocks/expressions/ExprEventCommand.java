package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.structures.StructEventListener;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The command in a PlayerCommandPreprocessEvent or ServerCommandEvent", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprEventCommand extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("event command");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(PlayerCommandPreprocessEvent.class, ServerCommandEvent.class);
    }

    @Override
    public String toJava() {
        return PlayerCommandPreprocessEvent.class.isAssignableFrom(((StructEventListener) getStatement().getStructure()).getEvent()) ?
                "event.getMessage()" :
                "event.getCommand()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ?
                PlayerCommandPreprocessEvent.class.isAssignableFrom(((StructEventListener) getStatement().getStructure()).getEvent()) ?
                        "event.setMessage(" + delta + ");" :
                        "event.setCommand(" + delta + ");" : null;
    }
}
