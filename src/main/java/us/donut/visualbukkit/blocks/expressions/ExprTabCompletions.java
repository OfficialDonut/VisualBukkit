package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The tab completions in a TabCompleteEvent", "Changers: set, add, remove", "Returns: list of strings"})
@Event(TabCompleteEvent.class)
public class ExprTabCompletions extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("tab completions");
    }

    @Override
    public String toJava() {
        return "event.getCompletions()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return "event.setCompletions(" + delta + ");";
            case ADD: return "event.getCompletions().add(" + delta + ");";
            case REMOVE: return "event.getCompletions().remove(" + delta + ");";
            case CLEAR: return "event.getCompletions().clear();";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return changeType == ChangeType.SET ? SimpleList.class : String.class;
    }
}
