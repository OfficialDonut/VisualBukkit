package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.Nameable;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The custom name of an entity", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprCustomName extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("custom name of", Nameable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getCustomName()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setCustomName(" + delta + ");" : null;
    }
}
