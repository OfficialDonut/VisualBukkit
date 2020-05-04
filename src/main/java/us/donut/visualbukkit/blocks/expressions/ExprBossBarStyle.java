package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The style of a boss bar", "Changers: set", "Returns: bar style"})
public class ExprBossBarStyle extends ChangeableExpressionBlock<BarStyle> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("style of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getStyle()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setStyle(" + delta + ");" : null;
    }
}
