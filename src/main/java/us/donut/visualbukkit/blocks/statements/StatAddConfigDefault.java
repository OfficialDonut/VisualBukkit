package us.donut.visualbukkit.blocks.statements;

import org.bukkit.configuration.Configuration;
import us.donut.visualbukkit.blocks.StatementBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description("Adds a default value to a config")
public class StatAddConfigDefault extends StatementBlock {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("set config default")
                .line("config:", Configuration.class)
                .line("key:   ", String.class)
                .line("value: ", Object.class);
    }

    @Override
    public String toJava() {
        return arg(0) + ".addDefault(" + arg(1) + "," + arg(2) + ");" +
                arg(0) + ".options().copyDefaults(true);";
    }
}
