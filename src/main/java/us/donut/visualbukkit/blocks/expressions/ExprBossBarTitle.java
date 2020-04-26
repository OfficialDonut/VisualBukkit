package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The title of a boss bar", "Changers: set", "Returns: string"})
public class ExprBossBarTitle extends ChangeableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("title of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        return changeType == ChangeType.SET ? arg(0) + ".setTitle(" + delta + ");" : null;
    }
}
