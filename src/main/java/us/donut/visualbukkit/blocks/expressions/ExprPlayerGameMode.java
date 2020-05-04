package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The game mode of a player", "Changers: set", "Returns: game mode"})
public class ExprPlayerGameMode extends ChangeableExpressionBlock<GameMode> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("game mode of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getGameMode()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setGameMode(" + delta + ");" : null;
    }
}
