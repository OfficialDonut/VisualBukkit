package com.gmail.visualbukkit.blocks.expressions;

import com.gmail.visualbukkit.blocks.ExpressionBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

@Description("The shooter of a projectile")
public class ExprProjectileShooter extends ExpressionBlock<ProjectileSource> {

    public ExprProjectileShooter() {
        init("shooter of ", Projectile.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getShooter()";
    }
}