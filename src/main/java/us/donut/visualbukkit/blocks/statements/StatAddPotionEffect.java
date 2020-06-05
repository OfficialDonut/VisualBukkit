package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description("Adds a potion effect to a living entity")
public class StatAddPotionEffect extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("add", PotionEffectType.class, "to", LivingEntity.class, "for", Duration.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addPotionEffect(new org.bukkit.potion.PotionEffect(" + arg(0) + ",(int)" + arg(2) + ".getSeconds() * 20,1),true);";
    }
}
