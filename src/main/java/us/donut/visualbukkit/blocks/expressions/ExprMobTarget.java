package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The target of a mob", "Returns: living entity"})
@Modifier(ModificationType.SET)
public class ExprMobTarget extends ExpressionBlock<LivingEntity> {

    @Override
    protected Syntax init() {
        return new Syntax("target of", Mob.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTarget()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setTarget(" + delta + ");" : null;
    }
}
