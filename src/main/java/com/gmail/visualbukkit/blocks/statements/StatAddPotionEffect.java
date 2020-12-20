package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

@Category(Category.ENTITY)
@Description("Adds a potion effect to a living entity")
public class StatAddPotionEffect extends StatementBlock {

    public StatAddPotionEffect() {
        init("add potion effect");
        initLine("effect:    ", PotionEffectType.class);
        initLine("entity:    ", LivingEntity.class);
        initLine("duration:  ", int.class, " (ticks)");
        initLine("amplifier: ", int.class);
        initLine("ambient:   ", boolean.class);
        initLine("particles: ", boolean.class);
        initLine("icon:      ", boolean.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addPotionEffect(new org.bukkit.potion.PotionEffect(" +
                arg(0) + "," + arg(2) + "," + arg(3) + "," + arg(4) + "," + arg(5) + "," + arg(6) + "),true);";
    }
}
