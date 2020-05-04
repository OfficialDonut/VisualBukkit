package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The visibility state of a boss bar", "Changers: set", "Returns: boolean"})
public class ExprBossBarVisibility extends ChangeableExpressionBlock<Boolean> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("visibility state of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".isVisible()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setVisible(" + delta + ");" : null;
    }
}
