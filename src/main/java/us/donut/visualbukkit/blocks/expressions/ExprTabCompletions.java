package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Event;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The tab completions in a TabCompleteEvent", "Returns: list of strings"})
@Event(TabCompleteEvent.class)
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprTabCompletions extends ModifiableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("tab completions");
    }

    @Override
    public String toJava() {
        return "event.getCompletions()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case SET: return "event.setCompletions(" + delta + ");";
            case ADD: return "event.getCompletions().add(" + delta + ");";
            case REMOVE: return "event.getCompletions().remove(" + delta + ");";
            case CLEAR: return "event.getCompletions().clear();";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return modificationType == ModificationType.SET ? SimpleList.class : String.class;
    }
}
