package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.util.List;

@Description({"The entries of a scoreboard team", "Returns: list of strings"})
@Modifier({ModificationType.ADD, ModificationType.REMOVE})
public class ExprTeamEntries extends ExpressionBlock<List> {

    @Override
    protected Syntax init() {
        return new Syntax("entries of", Team.class);
    }

    @Override
    public String toJava() {
        return "PluginMain.createList(" + arg(0) + ".getEntries())";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        switch (modificationType) {
            case ADD: return arg(0) + ".addEntry(PluginMain.color(" + delta + "));";
            case REMOVE: return arg(0) + ".removeEntry(PluginMain.color(" + delta + "));";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ModificationType modificationType) {
        return String.class;
    }
}
