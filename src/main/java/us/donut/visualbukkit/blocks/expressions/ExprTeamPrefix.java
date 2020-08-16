package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The prefix of a scoreboard team", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprTeamPrefix extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("prefix of", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getPrefix()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setPrefix(PluginMain.color(" + delta + "));" : null;
    }
}
