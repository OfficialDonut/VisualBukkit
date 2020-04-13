package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

import java.time.Duration;

@Description("Executes a runnable after a delay")
public class StatExecuteLater extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", Runnable.class, new ChoiceParameter("sync", "async"), "after", Duration.class);
    }

    @Override
    public String toJava() {
        String method = arg(1).equals("async") ? "Bukkit.getScheduler().runTaskLaterAsynchronously" : "Bukkit.getScheduler().runTaskLater";
        return method + "(this," + arg(0) + "," + arg(2) + ".getSeconds() * 20);";
    }
}
