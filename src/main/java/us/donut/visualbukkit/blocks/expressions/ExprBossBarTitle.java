package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.ModificationType;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.annotations.Modifier;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description({"The title of a boss bar", "Returns: string"})
@Modifier(ModificationType.SET)
public class ExprBossBarTitle extends ExpressionBlock<String> {

    @Override
    protected Syntax init() {
        return new Syntax("title of", BossBar.class);
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
