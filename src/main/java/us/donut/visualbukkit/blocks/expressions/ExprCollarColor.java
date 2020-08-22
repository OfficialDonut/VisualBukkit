package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.DyeColor;
import org.bukkit.entity.Wolf;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The collar color of a wolf", "Returns: dye color"})
@Modifier(ModificationType.SET)
public class ExprCollarColor extends ExpressionBlock<DyeColor> {

    @Override
    protected Syntax init() {
        return new Syntax("collar color of", Wolf.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".getCollarColor()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setCollarColor(" + delta + ");" : null;
    }
}
