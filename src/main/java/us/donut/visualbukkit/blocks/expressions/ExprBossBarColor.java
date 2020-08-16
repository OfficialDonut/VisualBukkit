package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The color of a boss bar", "Returns: bar color"})
@Modifier(ModificationType.SET)
public class ExprBossBarColor extends ExpressionBlock<BarColor> {

    @Override
    protected Syntax init() {
        return new Syntax("color of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getColor()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setColor(" + delta + ");" : null;
    }
}
