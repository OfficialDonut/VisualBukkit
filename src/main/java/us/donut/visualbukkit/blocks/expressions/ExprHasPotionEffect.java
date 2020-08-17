package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.BinaryChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"Checks if a living entity has a potion effect", "Returns: boolean"})
public class ExprHasPotionEffect extends ConditionBlock {

    @Override
    protected Syntax init() {
        return new Syntax(LivingEntity.class, new BinaryChoiceParameter("has", "does not have"), PotionEffectType.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".hasPotionEffect(" + arg(2) + ")";
    }
}
