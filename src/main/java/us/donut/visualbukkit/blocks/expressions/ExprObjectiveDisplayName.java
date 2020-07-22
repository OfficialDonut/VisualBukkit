package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The display name of a scoreboard objective", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprObjectiveDisplayName extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display name of", Objective.class);
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
