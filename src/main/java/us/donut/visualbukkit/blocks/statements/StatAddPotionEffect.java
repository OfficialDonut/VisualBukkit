package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

import java.time.Duration;

@Description("Adds a potion effect to a living entity")
public class StatAddPotionEffect extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("add potion effect", Syntax.LINE_SEPARATOR,
                "effect:  ", PotionEffectType.class, Syntax.LINE_SEPARATOR,
                "entity:  ", LivingEntity.class, Syntax.LINE_SEPARATOR,
                "duration:", Duration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addPotionEffect(new org.bukkit.potion.PotionEffect(" + arg(0) + ",(int)" + arg(2) + ".getSeconds() * 20,1),true);";
    }
}
