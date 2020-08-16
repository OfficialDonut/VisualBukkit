package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.event.server.TabCompleteEvent;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The tab completions in a TabCompleteEvent", "Returns: list of strings"})
@Modifier({ModificationType.SET, ModificationType.ADD, ModificationType.REMOVE})
public class ExprTabCompletions extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("tab completions");
    }

    @Override
    public void update() {
        super.update();
        validateEvent(TabCompleteEvent.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(event.getCompletions())";
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
        return modificationType == ModificationType.SET ? List.class : String.class;
    }
}
