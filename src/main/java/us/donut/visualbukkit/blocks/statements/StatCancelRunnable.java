package us.donut.visualbukkit.blocks.statements;

import org.bukkit.scheduler.BukkitRunnable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Cancels a runnable")
public class StatCancelRunnable extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("cancel", BukkitRunnable.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".cancel();";
    }
}
