package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The shooter of a projectile", "Returns: projectile source"})
@Modifier(ModificationType.SET)
public class ExprProjectileShooter extends ExpressionBlock<ProjectileSource> {

    @Override
    protected Syntax init() {
        return new Syntax("shooter of", Projectile.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getShooter()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setShooter(" + delta + ");" : null;
    }
}
