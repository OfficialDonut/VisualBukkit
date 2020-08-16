package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.WorldCreator;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The seed of a world creator", "Returns: number"})
@Modifier(ModificationType.SET)
public class ExprWorldCreatorSeed extends ExpressionBlock<Long> {

    @Override
    protected Syntax init() {
        return new Syntax("seed of", WorldCreator.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".seed()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".seed(" + delta + ");" : null;
    }
}
