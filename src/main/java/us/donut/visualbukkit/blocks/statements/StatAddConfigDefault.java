package us.donut.visualbukkit.blocks.statements;

import org.bukkit.configuration.Configuration;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Adds a default value to a config")
public class StatAddConfigDefault extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("set default for", String.class, "in", Configuration.class, "to", Object.class);
    }

    @Override
    public String toJava() {
        return arg(1) + ".addDefault(" + arg(0) + "," + arg(2) + ");" +
                arg(1) + ".options().copyDefaults(true);";
    }
}
