package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Sittable;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The sitting state of an entity that can sit", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprSittingState extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("sitting state of", Sittable.class);
    }

    @Override
    public String toJava() {
        return arg(0) +".isSitting()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setSitting(" + delta + ");" : null;
    }
}
