package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Tameable;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("The tamed state of a tameable entity")
@Modifier(ModificationType.SET)
public class ExprTamedState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("tamed state of", Tameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isTamed()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setTamed(" + delta + ");" : null;
    }
}
