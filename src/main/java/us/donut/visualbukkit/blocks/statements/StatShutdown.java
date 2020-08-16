package us.donut.visualbukkit.blocks.statements;

import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.Syntax;

@Description("Shuts down the server")
public class StatShutdown extends StatementBlock {

    @Override
    protected Syntax init() {
        return new Syntax("shutdown server");
    }

    @Override
    public String toJava() {
        return "Bukkit.shutdown();";
    }
}
