package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The style of a boss bar", "Returns: bar style"})
@Modifier(ModificationType.SET)
public class ExprBossBarStyle extends ModifiableExpressionBlock<BarStyle> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("style of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getStyle()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setStyle(" + delta + ");" : null;
    }
}
