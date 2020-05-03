package us.donut.visualbukkit.blocks.statements;

import org.bukkit.scheduler.BukkitRunnable;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description("Executes a runnable after a delay")
public class StatExecuteLater extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", BukkitRunnable.class, new ChoiceParameter("sync", "async"), "after", Duration.class);
    }

    @Override
    public String toJava() {
        String method = arg(1).equals("async") ? ".runTaskLaterAsynchronously" : ".runTaskLater";
        return arg(0) + method + "(PluginMain.getInstance()," + arg(2) + ".getSeconds() * 20);";
    }
}
