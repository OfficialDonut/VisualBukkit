package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The message recipients in an AsyncPlayerChatEvent", "Changers: clear, add, remove", "Returns: list of players"})
@Event(AsyncPlayerChatEvent.class)
public class ExprMessageRecipients extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("message recipients");
    }

    @Override
    public String toJava() {
        return "new SimpleList(event.getRecipients())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case CLEAR: return "event.getRecipients().clear();";
            case ADD: return "event.getRecipients().add(" + delta + ");";
            case REMOVE: return "event.getRecipients().remove(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return Player.class;
    }
}
