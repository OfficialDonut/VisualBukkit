package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The entries of a scoreboard team", "Changers: add, remove", "Returns: list of strings"})
public class ExprTeamEntries extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("entries of", Team.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getEntries())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case ADD: return arg(0) + ".addEntry(PluginMain.color(" + delta + "));";
            case REMOVE: return arg(0) + ".removeEntry(PluginMain.color(" + delta + "));";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return String.class;
    }
}
