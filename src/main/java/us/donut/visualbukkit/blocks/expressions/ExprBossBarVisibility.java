package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The visibility state of a boss bar", "Returns: boolean"})
@Modifier(ModificationType.SET)
public class ExprBossBarVisibility extends ExpressionBlock<Boolean> {

    @Override
    protected Syntax init() {
        return new Syntax("visibility state of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isVisible()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setVisible(" + delta + ");" : null;
    }
}
