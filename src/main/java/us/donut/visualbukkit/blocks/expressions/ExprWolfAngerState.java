package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Wolf;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The anger state of a wolf", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprWolfAngerState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("anger state of", Wolf.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isAngry()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setAngry(" + delta + ");" : null;
    }
}
