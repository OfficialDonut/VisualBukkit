package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description("Executes a runnable periodically")
public class StatExecutePeriodically extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", Runnable.class, new ChoiceParameter("sync", "async"), "every", Duration.class);
    }

    @Override
    public String toJava() {
        String method = arg(1).equals("async") ? "Bukkit.getScheduler().runTaskTimerAsynchronously" : "Bukkit.getScheduler().runTaskTimer";
        return method + "(this," + arg(0) + ",0L," + arg(2) + ".getSeconds() * 20);";
    }
}
