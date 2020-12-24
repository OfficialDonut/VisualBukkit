package com.gmail.visualbukkit.blocks.statements;

import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.annotations.Description;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;

@Description("Sets the shooter of a projectile")
public class StatSetProjectileShooter extends StatementBlock {

    public StatSetProjectileShooter() {
        init("set shooter of ", Projectile.class, " to ", ProjectileSource.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".setShooter(" + arg(1) + ");";
    }
}
