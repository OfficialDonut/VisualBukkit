package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Runs a command through console")
public class StatRunCommand extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("run command", String.class);
    }

    @Override
    public String toJava() {
        return "Bukkit.dispatchCommand(Bukkit.getConsoleSender()," + arg(0) + ");";
    }
}
