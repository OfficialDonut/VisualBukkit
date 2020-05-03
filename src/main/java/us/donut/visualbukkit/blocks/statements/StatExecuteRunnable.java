package us.donut.visualbukkit.blocks.statements;

import org.bukkit.scheduler.BukkitRunnable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Executes a runnable")
public class StatExecuteRunnable extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", BukkitRunnable.class, new ChoiceParameter("on current thread", "sync", "async"));
    }

    @Override
    public String toJava() {
        switch (arg(1)) {
            case "sync": return arg(0) + ".runTask(this);";
            case "async": return arg(0) + ".runTaskAsynchronously(this);";
            default: return arg(0) + ".run();";
        }
    }
}
