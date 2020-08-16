package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Team;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The suffix of a scoreboard team", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprTeamSuffix extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("suffix of", Team.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getSuffix()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setSuffix(PluginMain.color(" + delta + "));" : null;
    }
}
