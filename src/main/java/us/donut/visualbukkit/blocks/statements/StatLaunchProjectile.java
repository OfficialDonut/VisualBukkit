package us.donut.visualbukkit.blocks.statements;

import org.bukkit.entity.*;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.util.Map;
import java.util.TreeMap;

public class StatLaunchProjectile extends StatementBlock {

    private static Map<String, Class<? extends Projectile>> projectileClasses = new TreeMap<>();

    static {
        projectileClasses.put("arrow", Arrow.class);
        projectileClasses.put("dragon fireball", DragonFireball.class);
        projectileClasses.put("egg", Egg.class);
        projectileClasses.put("enderpearl", EnderPearl.class);
        projectileClasses.put("exp bottle", ThrownExpBottle.class);
        projectileClasses.put("fish hook", FishHook.class);
        projectileClasses.put("large fireball", LargeFireball.class);
        projectileClasses.put("llama spit", LlamaSpit.class);
        projectileClasses.put("shulker bullet", ShulkerBullet.class);
        projectileClasses.put("small fireball", SmallFireball.class);
        projectileClasses.put("snowball", Snowball.class);
        projectileClasses.put("spectral arrow", SpectralArrow.class);
        projectileClasses.put("trident", Trident.class);
        projectileClasses.put("wither skull", WitherSkull.class);
    }

    @Override
    protected SyntaxNode init() {
        ChoiceParameter choiceParameter = new ChoiceParameter(projectileClasses.keySet());
        return new SyntaxNode("launch", choiceParameter, "from", ProjectileSource.class, "with velocity", Vector.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".launchProjectile(" + projectileClasses.get(arg(0)).getCanonicalName() + ".class," + arg(2) + ");";
    }
}
