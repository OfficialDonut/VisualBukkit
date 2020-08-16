package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Player;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The displayed player list footer for a player", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprPlayerListFooter extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("player list footer of", Player.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPlayerListFooter()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setPlayerListFooter(" + delta + ");" : null;
    }
}
