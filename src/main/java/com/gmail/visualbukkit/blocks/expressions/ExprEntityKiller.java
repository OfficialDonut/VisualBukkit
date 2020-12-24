package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

@Description("The player who killed a living entity")
public class ExprEntityKiller extends ExpressionBlock<Player> {

    public ExprEntityKiller() {
        init("killer of ", LivingEntity.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getKiller()";
    }
}