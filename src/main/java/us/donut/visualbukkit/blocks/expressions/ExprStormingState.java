package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.World;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("The storming state in a world")
@Modifier(ModificationType.SET)
public class ExprStormingState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("storming state in", World.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".hasStorm()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setStorm(" + delta + ");" : null;
    }
}
