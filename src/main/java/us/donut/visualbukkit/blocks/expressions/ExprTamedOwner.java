package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Tameable;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The owner of a tameable entity", "Returns: animal tamer"})
@Modifier(ModificationType.SET)
public class ExprTamedOwner extends ExpressionBlock<AnimalTamer> {

    @Override
    protected Syntax init() {
        return new Syntax("owner of", Tameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getOwner()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setOwner(" + delta + ");" : null;
    }
}
