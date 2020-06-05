package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.ConditionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Checks if a living entity has a potion effect", "Returns: boolean"})
public class ExprHasPotionEffect extends ConditionBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode(LivingEntity.class, new ChoiceParameter("has", "does not have"), PotionEffectType.class);
    }

    @Override
    protected String toNonNegatedJava() {
        return arg(0) + ".hasPotionEffect(" + arg(2) + ")";
    }
}
