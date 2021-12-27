package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;

import java.util.Set;
import java.util.TreeSet;

public class StatLaunchProjectile extends Statement {

    private static Set<String> projectileTypes = new TreeSet<>();

    static {
        projectileTypes.add("Arrow");
        projectileTypes.add("DragonFireball");
        projectileTypes.add("Egg");
        projectileTypes.add("EnderPearl");
        projectileTypes.add("Fireball");
        projectileTypes.add("Firework");
        projectileTypes.add("FishHook");
        projectileTypes.add("LargeFireball");
        projectileTypes.add("LlamaSpit");
        projectileTypes.add("ShulkerBullet");
        projectileTypes.add("SmallFireball");
        projectileTypes.add("Snowball");
        projectileTypes.add("SpectralArrow");
        projectileTypes.add("ThrownExpBottle");
        projectileTypes.add("ThrownPotion");
        projectileTypes.add("Trident");
        projectileTypes.add("WitherSkull");
    }

    public StatLaunchProjectile() {
        super("stat-launch-projectile", "Launch Projectile", "World", "Launches a projectile");
    }

    @Override
    public Block createBlock() {
        return new Block(this, new ChoiceParameter("Type", projectileTypes), new ExpressionParameter("Source", ClassInfo.of("org.bukkit.projectiles.ProjectileSource")), new ExpressionParameter("Velocity", ClassInfo.of("org.bukkit.util.Vector"))) {
            @Override
            public String toJava() {
                return arg(1) + ".launchProjectile(org.bukkit.entity." + arg(0) + ".class," + arg(2) + ");";
            }
        };
    }
}
