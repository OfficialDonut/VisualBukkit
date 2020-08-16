package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The message recipients in an AsyncPlayerChatEvent", "Returns: list of players"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprEventMessageRecipients extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("message recipients");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(AsyncPlayerChatEvent.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(event.getRecipients())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case CLEAR: return "event.getRecipients().clear();";
            case ADD: return "event.getRecipients().add(" + delta + ");";
            case REMOVE: return "event.getRecipients().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return Player.class;
    }
}
