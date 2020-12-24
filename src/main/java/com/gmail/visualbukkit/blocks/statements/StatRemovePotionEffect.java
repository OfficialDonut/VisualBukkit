package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

@Category(Category.ENTITY)
@Description("Removes a potion effect from a living entity")
public class StatRemovePotionEffect extends StatementBlock {

    public StatRemovePotionEffect() {
        init("remove ", PotionEffectType.class, " from ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".removePotionEffect(" + arg(0) + ");";
    }
}
