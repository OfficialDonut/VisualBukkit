package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.ModifiableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The title of a boss bar", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprBossBarTitle extends ModifiableExpressionBlock<String> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("title of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getTitle()";
    }

    @Override
    public String modify(ModificationType modificationType, String delta) {
        return modificationType == ModificationType.SET ? arg(0) + ".setTitle(" + delta + ");" : null;
    }
}
