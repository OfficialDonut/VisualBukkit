package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The display slot of a scoreboard objective", "Returns: display slot"})
@Modifier(ModificationType.SET)
public class ExprObjectiveDisplaySlot extends ModifiableExpressionBlock<DisplaySlot> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("display slot of", Objective.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getDisplaySlot()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setDisplaySlot(" + delta + ");" : null;
    }
}
