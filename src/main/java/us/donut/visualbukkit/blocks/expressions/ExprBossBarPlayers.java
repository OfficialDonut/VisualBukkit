package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;
import us.donut.visualbukkit.util.SimpleList;

@Description({"The players viewing a boss bar", "Changers: clear, add, remove", "Returns: list of players"})
public class ExprBossBarPlayers extends ChangeableExpressionBlock<SimpleList> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("players of", BossBar.class);
    }

    @Override
    public String toJava() {
        return "new SimpleList(" + arg(0) + ".getPlayers())";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case CLEAR: return arg(0) + ".removeAll();";
            case ADD: return arg(0) + ".addPlayer(" + delta + ");";
            case REMOVE: return arg(0) + ".removePlayer(" + delta + ");";
            default: return null;
        }
    }

    @Override
    public Class<?> getDeltaType(ChangeType changeType) {
        return Player.class;
    }
}
