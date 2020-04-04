package us.donut.visualbukkit.blocks.expressions;

import org.bukkit.command.ConsoleCommandSender;
import us.donut.visualbukkit.blocks.ExpressionBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.SyntaxNode;

@Description({"The server console", "Returns: console command sender"})
public class ExprConsole extends ExpressionBlock<ConsoleCommandSender> {

    @Override
    protected SyntaxNode init() {
        return new SyntaxNode("console");
    }

    @Override
    public String toJava() {
        return "Bukkit.getConsoleSender()";
    }
}
