package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The color of a boss bar", "Changers: set", "Returns: bar color"})
public class ExprBossBarColor extends ChangeableExpressionBlock<BarColor> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("color of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getColor()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setColor(" + delta + ");" : null;
    }
}
