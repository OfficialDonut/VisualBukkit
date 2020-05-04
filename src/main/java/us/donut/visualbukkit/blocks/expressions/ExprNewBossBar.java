package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"Creates a new boss bar", "Returns: boss bar"})
public class ExprNewBossBar extends ExpressionBlock<BossBar> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("new boss bar with title", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.createBossBar(" + arg(0) + ", org.bukkit.boss.BarColor.BLUE, org.bukkit.boss.BarStyle.SOLID, new org.bukkit.boss.BarFlag[0])";
    }
}
