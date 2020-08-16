package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The display name of a scoreboard objective", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprObjectiveDisplayName extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("display name of", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplayName()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setDisplayName(PluginMain.color(" + delta + "));" : null;
    }
}
