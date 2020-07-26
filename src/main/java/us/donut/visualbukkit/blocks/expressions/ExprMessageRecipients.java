package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.List;

@Description({"The message recipients in an AsyncPlayerChatEvent", "Returns: list of players"})
@Event(AsyncPlayerChatEvent.class)
@Modifier({ModificationType.ADD, ModificationType.REMOVE, ModificationType.CLEAR})
public class ExprMessageRecipients extends ModifiableExpressionBlock<List> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("message recipients");
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
