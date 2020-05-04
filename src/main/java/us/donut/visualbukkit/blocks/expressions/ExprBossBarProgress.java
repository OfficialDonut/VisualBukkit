package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.boss.BossBar;
import us.donut.visualbukkit.blocks.ChangeType;
import us.donut.visualbukkit.blocks.ChangeableExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The progress of a boss bar", "Changers: set, add, remove", "Returns: number"})
public class ExprBossBarProgress extends ChangeableExpressionBlock<Double> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("progress of", BossBar.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".getProgress()";
    }

    @Override
    public String change(ChangeType changeType, String delta) {
        switch (changeType) {
            case SET: return arg(0) + ".setProgress(" + delta + ");";
            case ADD: return change(ChangeType.SET, toJava() + "-" + delta);
            case REMOVE: return change(ChangeType.SET, toJava() + "+" + delta);
            default: return null;
        }
    }
}
