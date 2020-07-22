package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The shooter of a projectile", "Returns: projectile source"})
@Modifier(ModificationType.SET)
public class ExprProjectileShooter extends ModifiableExpressionBlock<ProjectileSource> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("shooter of", Projectile.class);
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
