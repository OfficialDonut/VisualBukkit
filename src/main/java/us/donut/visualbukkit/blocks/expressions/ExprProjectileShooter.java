package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The shooter of a projectile", "Changers: set", "Returns: projectile source"})
public class ExprProjectileShooter extends ChangeableExpressionBlock<ProjectileSource> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("shooter of", Projectile.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getShooter()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setShooter(" + delta + ");" : null;
    }
}
