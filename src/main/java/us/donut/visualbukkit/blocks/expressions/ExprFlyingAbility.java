package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The flying ability of a player", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprFlyingAbility extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("flying ability of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".getAllowFlight()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setAllowFlight(" + delta + ");" : null;
    }
}
