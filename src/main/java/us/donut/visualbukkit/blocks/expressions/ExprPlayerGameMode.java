package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The game mode of a player", "Returns: game mode"})
@Modifier(ModificationType.SET)
public class ExprPlayerGameMode extends ExpressionBlock<GameMode> {

    @Override
    protected Syntax init() {
        return new Syntax("game mode of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getGameMode()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setGameMode(" + delta + ");" : null;
    }
}
