package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Executes a runnable")
public class StatExecuteRunnable extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("execute", Runnable.class, new ChoiceParameter("on current thread", "sync", "async"));
    }

    @Override
    public String toJava() {
        switch (arg(1)) {
            case "sync": return "Bukkit.getScheduler().runTask(this," + arg(0) + ");";
            case "async": return "Bukkit.getScheduler().runTaskAsynchronously(this," + arg(0) + ");";
            default: return arg(0) + ".run();";
        }
    }
}
